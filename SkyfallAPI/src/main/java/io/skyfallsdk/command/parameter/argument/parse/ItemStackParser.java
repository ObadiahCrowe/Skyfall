package net.treasurewars.core.command.parameter.argument.parse;

import net.treasurewars.core.command.parameter.argument.ArgumentParseException;
import net.treasurewars.core.command.parameter.argument.CommandArgument;
import net.treasurewars.core.util.UtilNumber;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class ItemStackParser implements ArgumentParser<ItemStack> {

    @Override
    public Collection<String> complete(CommandSender sender, CommandArgument<ItemStack> argument, String value) {
        return Arrays.stream(Material.values()).map(Material::name).collect(Collectors.toList());
    }

    @Override
    public Class[] getTypes() {
        return new Class[]{ItemStack.class};
    }

    @Override
    public ItemStack parse(CommandSender sender, Class type, String value) throws ArgumentParseException {
        if (!value.contains(":")) {
            return new ItemStack(this.parseMaterial(value));
        }

        String[] values = value.split(":");
        if (values.length != 2) {
            throw new ArgumentParseException("Could not parse item  \"" + value + "\": Invalid format");
        }

        Material material = this.parseMaterial(values[0]);
        String dataStr = values[1];
        if (!UtilNumber.isNumber(dataStr)) {
            throw new ArgumentParseException("Could not parse item  \"" + value + "\": Expected numerical data value");
        }

        int data = Integer.parseInt(dataStr);
        if (data > Short.MAX_VALUE) {
            throw new ArgumentParseException("Could not parse item  \"" + value + "\": Data exceeds max value of 32767");
        }

        return new ItemStack(material, 1, (short) data);
    }

    private Material parseMaterial(String str) {
        Material material;
        if (!UtilNumber.isNumber(str)) {
            int id = Integer.parseInt(str);
            material = Material.getMaterial(id);
        } else {
            material = Material.getMaterial(str);
        }

        if (material == null) {
            throw new ArgumentParseException("Could not parse material \"" + str + "\"");
        }

        return material;
    }
}
