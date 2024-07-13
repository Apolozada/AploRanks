package apolocode.ranks.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@AllArgsConstructor
@Data
public class Rank {

    private String id, name;
    private ItemStack icon;
    private List<String> commands;
    private double cust;
    private int position;

}