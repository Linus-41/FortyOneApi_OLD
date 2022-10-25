package me.linus41.fortyoneapi;


import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class ConfirmationMenu extends Menu {

    public ConfirmationMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return getName();
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {
        if(e.getCurrentItem().getType() == Material.BARRIER){
            backAction();
        }
        else if(e.getCurrentItem().getType() == Material.EMERALD){
            yesAction();
        }
        else if(e.getCurrentItem().getType() == Material.REDSTONE){
            noAction();
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack back = makeItem(Material.BARRIER,"Back");
        inventory.setItem(0,back);

        ItemStack yes = makeItem(Material.EMERALD,"Yes");
        inventory.setItem(3,yes);

        ItemStack no = makeItem(Material.REDSTONE,"No");
        inventory.setItem(5,no);
    }

    public abstract String getName();

    public abstract void yesAction();

    public abstract void noAction();

    public abstract void backAction();
}
