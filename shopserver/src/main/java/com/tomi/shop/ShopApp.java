package com.tomi.shop;

import com.tomi.shop.command.SeedDatabaseCommand;
import com.tomi.shop.core.Product;
import com.tomi.shop.db.DBSeeder;
import com.tomi.shop.db.ProductDAO;
import com.tomi.shop.filter.CORSFilter;
import com.tomi.shop.resources.ProductResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class ShopApp extends Application<ShopAppConfiguration> {

    public static void main(String[] args) throws Exception {
        new ShopApp().run(args);
    }

    private final HibernateBundle<ShopAppConfiguration> hibernateBundle =
            new HibernateBundle<ShopAppConfiguration>(Product.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(ShopAppConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };


    @Override
    public String getName() {
        return "shop-app";
    }

    @Override
    public void initialize(Bootstrap<ShopAppConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<ShopAppConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(ShopAppConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernateBundle);
//        bootstrap.addCommand(new SeedDatabaseCommand(hibernateBundle));
    }

    public void run(ShopAppConfiguration shopAppConfiguration, Environment environment) {
        System.out.println(environment.getName());
        environment.servlets().addFilter("CORSFilter", new CORSFilter())
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");

        final ProductDAO productDAO = new ProductDAO(hibernateBundle.getSessionFactory());
        if (!shopAppConfiguration.getEnvironment().toLowerCase().equals("test")) {
            seedDatabase(productDAO);
        }

        environment.jersey().register(new ProductResource(productDAO));
    }

    private void seedDatabase(ProductDAO productDAO) {
        DBSeeder dbSeeder = new UnitOfWorkAwareProxyFactory(hibernateBundle).create(DBSeeder.class, ProductDAO.class, productDAO);
        dbSeeder.createProducts();
    }

}
