package net.opticraft.bungee.plugin;

import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.opticraft.bungee.plugin.bans.BanComponentLoader;
import net.opticraft.bungee.plugin.core.OpticraftBungeeComponent;
import net.opticraft.bungee.plugin.core.OpticraftBungeeComponentLoader;
import net.opticraft.bungee.plugin.uuid.UuidService;
import net.opticraft.bungee.plugin.uuid.UuidServiceFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// This is the main entrypoint to this project.
public class OpticraftBungeePlugin extends Plugin {

	private final ArrayList<OpticraftBungeeComponent> enabledComponents;

	public OpticraftBungeePlugin() {
		enabledComponents = new ArrayList<>();
	}

    @Override
    public void onEnable() {
		// TODO: setup config
		Configuration configuration = loadConfig();

		UuidService uuidService = createUuidService(configuration);

		OpticraftBungeeComponentLoader loaders[] = new OpticraftBungeeComponentLoader[] {
				new BanComponentLoader(this, uuidService)
		};

		PluginManager pluginManager = getProxy().getPluginManager();

		for (OpticraftBungeeComponentLoader componentLoader : loaders) {
			componentLoader.initializeConfig(configuration);
			componentLoader.readConfig(configuration);
			if (componentLoader.shouldEnable()) {
				OpticraftBungeeComponent bungeeComponent = componentLoader.createComponent();
				enabledComponents.add(bungeeComponent);

				pluginManager.registerListener(this, bungeeComponent);
				for (Command command : bungeeComponent.getCommands()) {
					pluginManager.registerCommand(this, command);
				}
			}
		}
    }

	private UuidService createUuidService(Configuration configuration) {
		UuidServiceFactory factory = new UuidServiceFactory();
		factory.initializeConfig(configuration);
		return factory.create(getProxy().getScheduler().unsafe().getExecutorService(this));
	}


	private Configuration loadConfig() {
		try {
			File configFile = new File(getDataFolder(), "config.yml");
			if (!configFile.exists()) {
				configFile.createNewFile();
			}

			return ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public void onDisable() {
		super.onDisable();
		for (OpticraftBungeeComponent component : enabledComponents) {
			component.stop();
		}
	}
}