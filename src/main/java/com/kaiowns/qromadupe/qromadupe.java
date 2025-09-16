package com.kaiowns.qromadupe;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class qromadupe extends JavaPlugin implements Listener {

    private final Random random = new Random();
    private double dupeChance;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        loadConfigValues();

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    private void loadConfigValues() {
        FileConfiguration config = getConfig();
        dupeChance = config.getDouble("dupe-chance", 0.50); // default 50%
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        if (event.getResult() != null && event.getResult().hasItemMeta()
                && event.getResult().getItemMeta().hasDisplayName()) {

            event.setResult(event.getResult());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory() instanceof AnvilInventory)) return;
        if (event.getSlot() != 2) return;

        ItemStack result = event.getCurrentItem();
        if (result == null || !result.hasItemMeta() || !result.getItemMeta().hasDisplayName()) return;

        if (random.nextDouble() < dupeChance) {
            event.getWhoClicked().getInventory().addItem(result.clone());
        }
    }
}
