package alekseybykov.pets.mg.dao.h2updater;

import org.jetbrains.annotations.NotNull;

/**
 * @author bykov.alexey
 * @since 25.02.2024
 */
public interface H2UpdaterDao {

	void applyUpdates(@NotNull String sqlText);
}
