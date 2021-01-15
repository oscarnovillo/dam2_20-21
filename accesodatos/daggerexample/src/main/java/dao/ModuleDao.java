package dao;

import dagger.Binds;
import dagger.Module;

@Module
public interface ModuleDao {

    @Binds
    DaoMain getDaoMain(DaoMainImpl impl);
}
