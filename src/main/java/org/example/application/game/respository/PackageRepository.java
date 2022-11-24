package org.example.application.game.respository;

import org.example.application.game.model.card.Package;
import org.example.application.game.model.user.User;

import java.util.List;

public interface PackageRepository {
    Package getPackage(String id);

    Package getRandomPackage();

    List<Package> getPackages();

    Package addPackage(Package packages);
    Package deletePackages(Package packages);

    boolean addPackageToUser(Package packages, User user);
}
