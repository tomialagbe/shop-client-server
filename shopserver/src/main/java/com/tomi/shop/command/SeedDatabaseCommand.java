package com.tomi.shop.command;

import com.tomi.shop.ShopAppConfiguration;
import com.tomi.shop.db.DBSeeder;
import com.tomi.shop.db.ProductDAO;
import io.dropwizard.cli.Command;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

public class SeedDatabaseCommand extends Command {

    private HibernateBundle<ShopAppConfiguration> hibernateBundle;
    private ProductDAO productDAO;

    public SeedDatabaseCommand(HibernateBundle<ShopAppConfiguration> hibernateBundle) {
        super("dbseed", "seeds the database with initial data");
        this.hibernateBundle = hibernateBundle;
        this.productDAO = new ProductDAO(hibernateBundle.getSessionFactory());

    }

    @Override
    public void configure(Subparser subparser) {
    }

    @Override
    public void run(Bootstrap<?> bootstrap, Namespace namespace) throws Exception {
        DBSeeder dbSeeder = new UnitOfWorkAwareProxyFactory(hibernateBundle).create(DBSeeder.class, ProductDAO.class, productDAO);
        dbSeeder.createProducts();
    }
}
