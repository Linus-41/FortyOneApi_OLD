package me.linus41.fortyoneapi;

import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public abstract class PaginatedMenu extends Menu {

    //Keep track of what page the menu is on
    protected int page = 0;
    //28 is max items because with the border set below,
    //28 empty slots are remaining.
    protected int maxItemsPerPage = 36;
    //the index represents the index of the slot
    //that the loop is on
    protected int index = 0;

    protected ArrayList<ItemStack> itemStacksIterator;

    protected ItemStack filler, backButton, nextButton;

    public PaginatedMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public int getSlots(){
        return 54;
    }

    @Override
    public void setMenuItems() {

        for(int i=36; i<45; i++){
            filler = makeItem(Material.BLACK_STAINED_GLASS_PANE," ");
            inventory.setItem(i,filler);
        }

        backButton = makeItem(Material.DARK_OAK_BUTTON,"previous");
        inventory.setItem(48,backButton);

        nextButton = makeItem(Material.DARK_OAK_BUTTON,"next");
        inventory.setItem(50,nextButton);

        itemStacksIterator = setItemStackIterator();

        if(itemStacksIterator != null && !itemStacksIterator.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= itemStacksIterator.size()) break;
                if (itemStacksIterator.get(index) != null){
                    inventory.addItem(itemStacksIterator.get(index));
                }
            }
        }

        setItems();
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {
        Player player = (Player) e.getWhoClicked();

        if(e.getCurrentItem().equals(backButton)){
            e.setCancelled(true);
            if (page == 0){
                player.sendMessage(ChatColor.GRAY + "You are already on the first page.");
            }else{
                page = page - 1;
                super.open();
            }
        }
        else if(e.getCurrentItem().equals(nextButton)){
            e.setCancelled(true);
            if (!((index + 1) >= itemStacksIterator.size())){
                page = page + 1;
                super.open();
            }else{
                player.sendMessage(ChatColor.GRAY + "You are on the last page.");
            }
        }
        else if(e.getCurrentItem().equals(filler)){
            e.setCancelled(true);
        }
        else {
            handleClicks(e);
        }
    }

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }

    public abstract ArrayList<ItemStack> setItemStackIterator();

    public abstract void handleClicks(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException;

    public abstract void setItems();

}
