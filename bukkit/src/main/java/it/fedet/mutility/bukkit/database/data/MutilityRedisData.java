package it.fedet.mutility.bukkit.database.data;

import it.fedet.mutility.common.database.redis.RedisProvider;
import it.fedet.mutility.common.database.redis.data.RedisData;
import it.fedet.mutility.common.server.chat.ConsoleLogger;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MutilityRedisData extends RedisData {

    private static final String PREFIX = "staffmode:";

    public MutilityRedisData(RedisProvider provider) {
        super(provider);
    }

    public void enableStaffMode(Player player) {
        try (StatefulRedisConnection<String, String> connection = getConnection()) {
            RedisCommands<String, String> commands = connection.sync();

            commands.set(PREFIX + player.getUniqueId().toString(), "true");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disableStaffMode(Player player) {
        try (StatefulRedisConnection<String, String> connection = getConnection()) {
            RedisCommands<String, String> commands = connection.sync();

            commands.del(PREFIX + player.getUniqueId().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<String> getStaffMode(Player player) {
        try (StatefulRedisConnection<String, String> connection = getConnection()) {
            RedisCommands<String, String> commands = connection.sync();

            return Optional.ofNullable(commands.get(PREFIX + player.getUniqueId().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<String> getData(String key) {
        try (StatefulRedisConnection<String, String> connection = getConnection()) {
            return Optional.ofNullable(connection.sync().get(key));
        } catch (Exception ignore) {}

        return Optional.empty();
    }

    public void setData(String key, String value) {
        try (StatefulRedisConnection<String, String> connection = getConnection()) {
            connection.sync().set(key, value);
        } catch (Exception ignore) {}
    }

    public void removeData(String ...keys) {
        try (StatefulRedisConnection<String, String> connection = getConnection()) {
            connection.sync().del(keys);
        } catch (Exception ignore) {}
    }

    public void addData(String key, String value) {
        try (StatefulRedisConnection<String, String> connection = getConnection()) {
            RedisCommands<String, String> command = connection.sync();
            command.set(key, command.get(key) + value + ":");
        } catch (Exception ignore) {}
    }

    public void removeData(String key, String value) {
        try (StatefulRedisConnection<String, String> connection = getConnection()) {
            RedisCommands<String, String> command = connection.sync();
            String[] split = command.get(key).split(":");
            List<String> newValue = new ArrayList<>();

            for (String currentValue : split)
                if (!currentValue.equals(value)) newValue.add(currentValue);

            ConsoleLogger.info("Lista aggiornata: " + newValue);
            String insertValue = String.join(":", newValue);

            command.set(key, insertValue.isEmpty() ? "" : insertValue + ":");
        } catch (Exception ignore) {}
    }
}
