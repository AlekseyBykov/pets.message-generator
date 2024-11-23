package alekseybykov.pets.mg.dao.h2updater;

import org.jetbrains.annotations.NotNull;

public interface H2UpdaterDao {

	void applyUpdates(@NotNull String sqlText);
}
