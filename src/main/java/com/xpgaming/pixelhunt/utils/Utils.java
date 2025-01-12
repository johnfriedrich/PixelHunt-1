package com.xpgaming.pixelhunt.utils;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.implementation.tasks.Task;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.economy.IPixelmonBankAccount;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import com.pixelmonmod.pixelmon.enums.EnumNature;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import com.xpgaming.pixelhunt.Config;
import com.xpgaming.pixelhunt.PixelHuntForge;
import com.xpgaming.pixelhunt.enums.EnumItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;


import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xpgaming.pixelhunt.PixelHuntForge.*;

public class Utils {

    private static final Utils instance = new Utils();

    public static Task announcementTask = null;
    public static Task huntTimer1 = null;
    public static Task huntTimer2 = null;
    public static Task huntTimer3 = null;
    public static Task huntTimer4 = null;

    public static Utils getInstance() {
        return instance;
    }

    public static void giveItemStack(ItemStack i, EntityPlayerMP p) {
        boolean foundEmpty = false;
        for (ItemStack stack : p.inventory.mainInventory) {
            if (stack != null) {
                foundEmpty = true;
                break;
            }
        }
        if (foundEmpty) {
            p.inventory.addItemStackToInventory(i);
        } else {
            World world = p.world;
            BlockPos pos = p.getPosition();
            EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ());
            entityItem.setItem(i);
            world.spawnEntity(entityItem);
        }
    }

    public static void addMoney(EntityPlayerMP p, int amount) {
        IPixelmonBankAccount bankAccount = Pixelmon.moneyManager.getBankAccount(p).get();
        bankAccount.updatePlayer(bankAccount.getMoney() + amount);
    }


    public static void reloadAnnouncementTask() {

        if (announcementTask != null) {
            announcementTask.setExpired();
        }

        announcementTask = Task.builder()
                .execute(task -> {
                    if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "global-msg").getBoolean()) {
                        sendServer(Utils.prefix() + Utils.lang("announcement-message"));
                    }
                })
                .interval((Config.getInstance().getConfig().getNode("pixelhunt", "general", "announcement-timer").getInt() * 60L) * 20)
                .infinite()
                .build();
    }

    private static final SecureRandom random = new SecureRandom();

    public static String rewardList(int num) {
        String strList = "";
        String balls = Config.getInstance().getConfig().getNode("pixelhunt", "lang", "balls").getString();
        String money = Config.getInstance().getConfig().getNode("pixelhunt", "lang", "money").getString();
        String candies = Config.getInstance().getConfig().getNode("pixelhunt", "lang", "candies").getString();
        String other = Config.getInstance().getConfig().getNode("pixelhunt", "lang", "other").getString();
        if (num == 1) {

            if (Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "give-balls").getBoolean())
                strList = "\u00A7d" + balls + ": \u00A7f" + PixelHuntForge.pokemon1ballReward.getCount() + " ";

            if (Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "give-money").getBoolean())
                strList = strList + "\u00A72" + money + ": \u00A7f" + PixelHuntForge.pokemon1moneyReward + " ";

            if (Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "give-candy").getBoolean())
                strList = strList + "\u00A73" + candies + ": \u00A7f" + PixelHuntForge.pokemon1rc.getCount() + " ";

            if (Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-toggle").getBoolean())
                strList = strList + "\u00A74" + other + ": \u00A7f" + PixelHuntForge.pokemon1msg + " ";
        } else if (num == 2) {
            if (Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "give-balls").getBoolean())
                strList = "\u00A7d" + balls + ": \u00A7f" + PixelHuntForge.pokemon2ballReward.getCount() + " ";

            if (Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "give-money").getBoolean())
                strList = strList + "\u00A72" + money + ": \u00A7f" + PixelHuntForge.pokemon2moneyReward + " ";

            if (Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "give-candy").getBoolean())
                strList = strList + "\u00A73" + candies + ": \u00A7f" + PixelHuntForge.pokemon2rc.getCount() + " ";

            if (Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-toggle").getBoolean())
                strList = strList + "\u00A74" + other + ": \u00A7f" + PixelHuntForge.pokemon2msg + " ";
        } else if (num == 3) {
            if (Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "give-balls").getBoolean())
                strList = "\u00A7d" + balls + ": \u00A7f" + PixelHuntForge.pokemon3ballReward.getCount() + " ";

            if (Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "give-money").getBoolean())
                strList = strList + "\u00A72" + money + ": \u00A7f" + PixelHuntForge.pokemon3moneyReward + " ";

            if (Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "give-candy").getBoolean())
                strList = strList + "\u00A73" + candies + ": \u00A7f" + PixelHuntForge.pokemon3rc.getCount() + " ";

            if (Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-toggle").getBoolean())
                strList = strList + "\u00A74" + other + ": \u00A7f" + PixelHuntForge.pokemon3msg + " ";
        } else if (num == 4) {
            if (Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "give-balls").getBoolean())
                strList = "\u00A7d" + balls + ": \u00A7f" + PixelHuntForge.pokemon4ballReward.getCount() + " ";

            if (Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "give-money").getBoolean())
                strList = strList + "\u00A72" + money + ": \u00A7f" + PixelHuntForge.pokemon4moneyReward + " ";

            if (Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "give-candy").getBoolean())
                strList = strList + "\u00A73" + candies + ": \u00A7f" + PixelHuntForge.pokemon4rc.getCount() + " ";

            if (Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-toggle").getBoolean())
                strList = strList + "\u00A74" + other + ": \u00A7f" + PixelHuntForge.pokemon4msg + " ";
        } else {
            strList = "Error!";
        }

        return strList;
    }

    public void randomisePokemon(int slot) {
        switch (slot) {
            case 1:
                cancelTimers(1);
                if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                    PixelHuntForge.pokemon1 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
                else PixelHuntForge.pokemon1 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
                while (getExcludedPokemon().contains(PixelHuntForge.pokemon1)) {
                    if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                        PixelHuntForge.pokemon1 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
                    else PixelHuntForge.pokemon1 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
                }
                PixelHuntForge.nature1 = randomEnum(EnumNature.class).toString();
                PixelHuntForge.nature1b = randomEnum(EnumNature.class).toString();
                while (PixelHuntForge.nature1b.equalsIgnoreCase(PixelHuntForge.nature1))
                    PixelHuntForge.nature1b = randomEnum(EnumNature.class).toString();
                PixelHuntForge.nature1c = randomEnum(EnumNature.class).toString();
                while (PixelHuntForge.nature1c.equalsIgnoreCase(PixelHuntForge.nature1b) || PixelHuntForge.nature1c.equalsIgnoreCase(PixelHuntForge.nature1))
                    PixelHuntForge.nature1c = randomEnum(EnumNature.class).toString();
                if (getUncommonPokemon().contains(PixelHuntForge.pokemon1))
                    PixelHuntForge.pokemon1rc = randomRareCandy(1);
                else PixelHuntForge.pokemon1rc = randomRareCandy(0);
                if (getUncommonPokemon().contains(PixelHuntForge.pokemon1)) {
                    PixelHuntForge.pokemon1ballReward = randomBall(1, 1);
                    PixelHuntForge.pokemon1moneyReward = randomMoney(1);
                    PixelHuntForge.pokemon1msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-uncommon-msg").getString();
                } else if (Utils.getRarePokemon().contains(PixelHuntForge.pokemon1)) {
                    PixelHuntForge.pokemon1rc = randomRareCandy(2);
                    PixelHuntForge.pokemon1ballReward = randomBall(1, 2);
                    PixelHuntForge.pokemon1moneyReward = randomMoney(2);
                    PixelHuntForge.pokemon1msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-rare-msg").getString();
                } else {
                    PixelHuntForge.pokemon1ballReward = randomBall(1, 0);
                    PixelHuntForge.pokemon1moneyReward = randomMoney(0);
                    PixelHuntForge.pokemon1msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-common-msg").getString();
                }
                startTimer(1);
                break;
            case 2:
                cancelTimers(2);
                if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                    pokemon2 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
                else pokemon2 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
                while (getExcludedPokemon().contains(pokemon2)) {
                    if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                        pokemon2 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
                    else pokemon2 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
                }
                PixelHuntForge.nature2 = randomEnum(EnumNature.class).toString();
                PixelHuntForge.nature2b = randomEnum(EnumNature.class).toString();
                while (PixelHuntForge.nature2b.equalsIgnoreCase(PixelHuntForge.nature2))
                    PixelHuntForge.nature2b = randomEnum(EnumNature.class).toString();
                PixelHuntForge.nature2c = randomEnum(EnumNature.class).toString();
                while (PixelHuntForge.nature2c.equalsIgnoreCase(PixelHuntForge.nature2b) || PixelHuntForge.nature2c.equalsIgnoreCase(PixelHuntForge.nature2))
                    PixelHuntForge.nature2c = randomEnum(EnumNature.class).toString();
                if (getUncommonPokemon().contains(pokemon2))
                    PixelHuntForge.pokemon2rc = randomRareCandy(1);
                else PixelHuntForge.pokemon2rc = randomRareCandy(0);
                if (getUncommonPokemon().contains(pokemon2)) {
                    PixelHuntForge.pokemon2ballReward = randomBall(2, 1);
                    PixelHuntForge.pokemon2moneyReward = randomMoney(1);
                    PixelHuntForge.pokemon2msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-uncommon-msg").getString();
                } else if (Utils.getRarePokemon().contains(pokemon2)) {
                    PixelHuntForge.pokemon2rc = randomRareCandy(2);
                    PixelHuntForge.pokemon2ballReward = randomBall(2, 2);
                    PixelHuntForge.pokemon2moneyReward = randomMoney(2);
                    PixelHuntForge.pokemon2msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-rare-msg").getString();
                } else {
                    PixelHuntForge.pokemon2ballReward = randomBall(2, 0);
                    PixelHuntForge.pokemon2moneyReward = randomMoney(0);
                    PixelHuntForge.pokemon2msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-common-msg").getString();
                }
                startTimer(2);
                break;
            case 3:
                cancelTimers(3);
                if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                    PixelHuntForge.pokemon3 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
                else PixelHuntForge.pokemon3 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
                while (getExcludedPokemon().contains(PixelHuntForge.pokemon3)) {
                    if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                        PixelHuntForge.pokemon3 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
                    else PixelHuntForge.pokemon3 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
                }
                PixelHuntForge.nature3 = randomEnum(EnumNature.class).toString();
                PixelHuntForge.nature3b = randomEnum(EnumNature.class).toString();
                while (PixelHuntForge.nature3b.equalsIgnoreCase(PixelHuntForge.nature3))
                    PixelHuntForge.nature3b = randomEnum(EnumNature.class).toString();
                PixelHuntForge.nature3c = randomEnum(EnumNature.class).toString();
                while (PixelHuntForge.nature3c.equalsIgnoreCase(PixelHuntForge.nature3b) || PixelHuntForge.nature3c.equalsIgnoreCase(PixelHuntForge.nature3))
                    PixelHuntForge.nature3c = randomEnum(EnumNature.class).toString();
                if (getUncommonPokemon().contains(PixelHuntForge.pokemon3))
                    PixelHuntForge.pokemon3rc = randomRareCandy(1);
                else PixelHuntForge.pokemon3rc = randomRareCandy(0);
                if (getUncommonPokemon().contains(PixelHuntForge.pokemon3)) {
                    PixelHuntForge.pokemon3ballReward = randomBall(3, 1);
                    PixelHuntForge.pokemon3moneyReward = randomMoney(1);
                    PixelHuntForge.pokemon3msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-uncommon-msg").getString();
                } else if (Utils.getRarePokemon().contains(PixelHuntForge.pokemon3)) {
                    PixelHuntForge.pokemon3rc = randomRareCandy(2);
                    PixelHuntForge.pokemon3ballReward = randomBall(3, 2);
                    PixelHuntForge.pokemon3moneyReward = randomMoney(2);
                    PixelHuntForge.pokemon3msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-rare-msg").getString();
                } else {
                    PixelHuntForge.pokemon3ballReward = randomBall(3, 0);
                    PixelHuntForge.pokemon3moneyReward = randomMoney(0);
                    PixelHuntForge.pokemon3msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-common-msg").getString();
                }
                startTimer(3);
                break;
            case 4:
                cancelTimers(4);
                if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                    PixelHuntForge.pokemon4 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
                else PixelHuntForge.pokemon4 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
                while (getExcludedPokemon().contains(PixelHuntForge.pokemon4)) {
                    if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                        PixelHuntForge.pokemon4 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
                    else PixelHuntForge.pokemon4 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
                }
                PixelHuntForge.nature4 = randomEnum(EnumNature.class).toString();
                PixelHuntForge.nature4b = randomEnum(EnumNature.class).toString();
                while (PixelHuntForge.nature4b.equalsIgnoreCase(PixelHuntForge.nature4))
                    PixelHuntForge.nature4b = randomEnum(EnumNature.class).toString();
                PixelHuntForge.nature4c = randomEnum(EnumNature.class).toString();
                while (PixelHuntForge.nature4c.equalsIgnoreCase(PixelHuntForge.nature4b) || PixelHuntForge.nature4c.equalsIgnoreCase(PixelHuntForge.nature4))
                    PixelHuntForge.nature4c = randomEnum(EnumNature.class).toString();
                if (getUncommonPokemon().contains(PixelHuntForge.pokemon4))
                    PixelHuntForge.pokemon4rc = randomRareCandy(1);
                else PixelHuntForge.pokemon4rc = randomRareCandy(0);
                if (getUncommonPokemon().contains(PixelHuntForge.pokemon4)) {
                    PixelHuntForge.pokemon4ballReward = randomBall(4, 1);
                    PixelHuntForge.pokemon4moneyReward = randomMoney(1);
                    PixelHuntForge.pokemon4msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-uncommon-msg").getString();
                } else if (Utils.getRarePokemon().contains(PixelHuntForge.pokemon4)) {
                    PixelHuntForge.pokemon4rc = randomRareCandy(2);
                    PixelHuntForge.pokemon4ballReward = randomBall(4, 2);
                    PixelHuntForge.pokemon4moneyReward = randomMoney(2);
                    PixelHuntForge.pokemon4msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-rare-msg").getString();
                } else {
                    PixelHuntForge.pokemon4ballReward = randomBall(4, 0);
                    PixelHuntForge.pokemon4moneyReward = randomMoney(0);
                    PixelHuntForge.pokemon4msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-common-msg").getString();
                }
                startTimer(4);
                break;
        }

    }

    public static <T extends Enum<?>> T randomEnum(Class<T> enumClass) {
        int x = random.nextInt(enumClass.getEnumConstants().length);
        return enumClass.getEnumConstants()[x];
    }


    private static final String regex = "&(?=[0-9a-fk-or])";
    private static final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

    public static String regex(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            line = line.replaceAll(regex, "§");
        }
        return line;
    }

    public static Button colouredPane(int num) {
        return GooeyButton.builder()
                .title("")
                .display(new net.minecraft.item.ItemStack(Blocks.STAINED_GLASS_PANE, 1, num))
                .build();
    }

    public static int getButtonPos(String node, int pos) {
        String[] numString = Config.getInstance().getConfig().getNode("pixelhunt", "gui", node).getString().split(", ");
        switch (pos) {
            case 1:
                return Integer.valueOf(numString[0]);
            case 2:
                return Integer.valueOf(numString[1]);
            default:
                return -1;
        }
    }


    public static List<String> getHuntInfo(String nature, String reward, String expiry) {
        List<String> lore = new ArrayList<>();
        lore.add(regex("&a&l" + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "natures").getString() + ":"));
        switch (nature) {
            case "nature1":
                lore.add(regex("&e- " + PixelHuntForge.nature1));
                lore.add(regex("&e- " + PixelHuntForge.nature1b));
                lore.add(regex("&e- " + PixelHuntForge.nature1c));
                break;
            case "nature2":
                lore.add(regex("&e- " + PixelHuntForge.nature2));
                lore.add(regex("&e- " + PixelHuntForge.nature2b));
                lore.add(regex("&e- " + PixelHuntForge.nature2c));
                break;
            case "nature3":
                lore.add(regex("&e- " + PixelHuntForge.nature3));
                lore.add(regex("&e- " + PixelHuntForge.nature3b));
                lore.add(regex("&e- " + PixelHuntForge.nature3c));
                break;
            case "nature4":
                lore.add(regex("&e- " + PixelHuntForge.nature4));
                lore.add(regex("&e- " + PixelHuntForge.nature4b));
                lore.add(regex("&e- " + PixelHuntForge.nature4c));
                break;
        }

        lore.add(regex("&a&l" + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "rewards").getString() + ":"));
        switch (reward) {
            case "reward1":
                lore.add(regex("&e- " + rewardList(1)));
                break;
            case "reward2":
                lore.add(regex("&e- " + rewardList(2)));
                break;
            case "reward3":
                lore.add(regex("&e- " + rewardList(3)));
                break;
            case "reward4":
                lore.add(regex("&e- " + rewardList(4)));
                break;
        }

        lore.add(regex("&a&l" + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "expiry").getString() + ":"));

        LocalDateTime timeNow = LocalDateTime.now();
        switch (expiry) {
            case "expiry1":
                lore.add(regex("&e- " + Utils.getInstance().calculateTimeDifference(timeNow, PixelHuntForge.pokemon1expiry)));
                break;
            case "expiry2":
                lore.add(regex("&e- " + Utils.getInstance().calculateTimeDifference(timeNow, PixelHuntForge.pokemon2expiry)));
                break;
            case "expiry3":
                lore.add(regex("&e- " + Utils.getInstance().calculateTimeDifference(timeNow, PixelHuntForge.pokemon3expiry)));
                break;
            case "expiry4":
                lore.add(regex("&e- " + Utils.getInstance().calculateTimeDifference(timeNow, PixelHuntForge.pokemon4expiry)));
                break;
        }
        return lore;

    }

    public static net.minecraft.item.ItemStack getPokemonPhoto(Pokemon pokemon) {
        return ItemPixelmonSprite.getPhoto(pokemon);
    }


    public static String sanitisePokemon(String poke) {
        if (poke.equalsIgnoreCase("MrMime")) return "Mr. Mime";
        else if (poke.equalsIgnoreCase("MimeJr")) return "Mime Jr.";
        else if (poke.equalsIgnoreCase("Nidoranfemale")) return "Nidoran\u2640";
        else if (poke.equalsIgnoreCase("Nidoranmale")) return "Nidoran\u2642";
        else if (poke.equalsIgnoreCase("Farfetchd")) return "Farfetch'd";
        return poke;
    }

    public String calculateTimeDifference(LocalDateTime from, LocalDateTime to) {

        LocalDateTime fromTemp = LocalDateTime.from(from);
        long years = fromTemp.until(to, ChronoUnit.YEARS);
        fromTemp = fromTemp.plusYears(years);

        long months = fromTemp.until(to, ChronoUnit.MONTHS);
        fromTemp = fromTemp.plusMonths(months);

        long days = fromTemp.until(to, ChronoUnit.DAYS);
        fromTemp = fromTemp.plusDays(days);

        long hours = fromTemp.until(to, ChronoUnit.HOURS);
        fromTemp = fromTemp.plusHours(hours);

        long minutes = fromTemp.until(to, ChronoUnit.MINUTES);
        fromTemp = fromTemp.plusMinutes(minutes);

        long seconds = fromTemp.until(to, ChronoUnit.SECONDS);
        fromTemp = fromTemp.plusSeconds(seconds);

        String unitDays = Config.getInstance().getConfig().getNode("pixelhunt", "lang", "unitDays").getString();
        String unitHours = Config.getInstance().getConfig().getNode("pixelhunt", "lang", "unitHours").getString();
        String unitMinutes = Config.getInstance().getConfig().getNode("pixelhunt", "lang", "unitMinutes").getString();
        String unitSeconds = Config.getInstance().getConfig().getNode("pixelhunt", "lang", "unitSeconds").getString();
        return "&f" + days + "&b" + unitDays + " &f" + hours + "&b" + unitHours + " &f" + minutes + "&b" + unitMinutes + " &f" + seconds + "&b" + unitSeconds;
    }

    public int isInHunt(Pokemon pokemon) {
        if (pokemon == null) {
            return 0;
        }
        Pokemon pokemon1 = Pixelmon.pokemonFactory.create(EnumSpecies.getFromNameAnyCase(PixelHuntForge.pokemon1));
        Pokemon pokemon2 = Pixelmon.pokemonFactory.create(EnumSpecies.getFromNameAnyCase(PixelHuntForge.pokemon2));
        Pokemon pokemon3 = Pixelmon.pokemonFactory.create(EnumSpecies.getFromNameAnyCase(PixelHuntForge.pokemon3));
        Pokemon pokemon4 = Pixelmon.pokemonFactory.create(EnumSpecies.getFromNameAnyCase(PixelHuntForge.pokemon4));

        if (pokemon.getSpecies() == pokemon1.getSpecies()) {
            return 1;
        } else if (pokemon.getSpecies() == pokemon2.getSpecies()) {
            return 2;
        } else if (pokemon.getSpecies() == pokemon3.getSpecies()) {
            return 3;
        } else if (pokemon.getSpecies() == pokemon4.getSpecies()) {
            return 4;
        } else {
            return 0;
        }
    }

    public void startTimer(int slot) {
        int min = Config.getInstance().getConfig().getNode("pixelhunt", "general", "common-pokemon-timer-min").getInt();
        int max = Config.getInstance().getConfig().getNode("pixelhunt", "general", "common-pokemon-timer-max").getInt();
        int hours = random.nextInt(max - min + 1) + min;
        LocalDateTime date = LocalDateTime.now();
        switch (slot) {
            case 1:
                cancelTimers(1);
                if (getUncommonPokemon().contains(PixelHuntForge.pokemon1)) {
                    min = Config.getInstance().getConfig().getNode("pixelhunt", "general", "uncommon-pokemon-timer-min").getInt();
                    max = Config.getInstance().getConfig().getNode("pixelhunt", "general", "uncommon-pokemon-timer-max").getInt();
                } else if (Utils.getRarePokemon().contains(PixelHuntForge.pokemon1)) {
                    min = Config.getInstance().getConfig().getNode("pixelhunt", "general", "rare-pokemon-timer-min").getInt();
                    max = Config.getInstance().getConfig().getNode("pixelhunt", "general", "rare-pokemon-timer-max").getInt();
                }
                hours = random.nextInt(max - min + 1) + min;
                if (min == max) hours = max;
                date = date.plusHours(hours);
                PixelHuntForge.pokemon1expiry = date;

                huntTimer1 = Task.builder()
                        .execute(task -> {
                            sendServer(prefix() + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "hunt-ended").getString().replace("<pokemon>", PixelHuntForge.pokemon1));
                            randomisePokemon(slot);
                            sendServer(prefix() + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "new-hunt").getString().replace("<pokemon>", PixelHuntForge.pokemon1));
                        })
                        .delay((hours * 3600L) * 20L)
                        .build();
                break;
            case 2:
                cancelTimers(2);
                if (getUncommonPokemon().contains(PixelHuntForge.pokemon2)) {
                    min = Config.getInstance().getConfig().getNode("pixelhunt", "general", "uncommon-pokemon-timer-min").getInt();
                    max = Config.getInstance().getConfig().getNode("pixelhunt", "general", "uncommon-pokemon-timer-max").getInt();
                } else if (Utils.getRarePokemon().contains(PixelHuntForge.pokemon2)) {
                    min = Config.getInstance().getConfig().getNode("pixelhunt", "general", "rare-pokemon-timer-min").getInt();
                    max = Config.getInstance().getConfig().getNode("pixelhunt", "general", "rare-pokemon-timer-max").getInt();
                }
                hours = random.nextInt(max - min + 1) + min;
                date = date.plusHours(hours);
                if (min == max) hours = max;
                PixelHuntForge.pokemon2expiry = date;

                huntTimer2 = Task.builder()
                        .execute(task -> {
                            sendServer(prefix() + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "hunt-ended").getString().replace("<pokemon>", PixelHuntForge.pokemon2));
                            randomisePokemon(slot);
                            sendServer(prefix() + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "new-hunt").getString().replace("<pokemon>", PixelHuntForge.pokemon2));
                        })
                        .delay((hours * 3600L) * 20L)
                        .build();
                break;
            case 3:
                cancelTimers(3);
                if (getUncommonPokemon().contains(PixelHuntForge.pokemon3)) {
                    min = Config.getInstance().getConfig().getNode("pixelhunt", "general", "uncommon-pokemon-timer-min").getInt();
                    max = Config.getInstance().getConfig().getNode("pixelhunt", "general", "uncommon-pokemon-timer-max").getInt();
                } else if (Utils.getRarePokemon().contains(PixelHuntForge.pokemon3)) {
                    min = Config.getInstance().getConfig().getNode("pixelhunt", "general", "rare-pokemon-timer-min").getInt();
                    max = Config.getInstance().getConfig().getNode("pixelhunt", "general", "rare-pokemon-timer-max").getInt();
                }
                hours = random.nextInt(max - min + 1) + min;
                if (min == max) hours = max;
                date = date.plusHours(hours);
                PixelHuntForge.pokemon3expiry = date;

                huntTimer3 = Task.builder()
                        .execute(task -> {
                            sendServer(prefix() + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "hunt-ended").getString().replace("<pokemon>", PixelHuntForge.pokemon3));
                            randomisePokemon(slot);
                            sendServer(prefix() + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "new-hunt").getString().replace("<pokemon>", PixelHuntForge.pokemon3));
                        })
                        .delay((hours * 3600L) * 20L)
                        .build();
                break;
            case 4:
                cancelTimers(4);
                if (getUncommonPokemon().contains(PixelHuntForge.pokemon4)) {
                    min = Config.getInstance().getConfig().getNode("pixelhunt", "general", "uncommon-pokemon-timer-min").getInt();
                    max = Config.getInstance().getConfig().getNode("pixelhunt", "general", "uncommon-pokemon-timer-max").getInt();
                } else if (Utils.getRarePokemon().contains(PixelHuntForge.pokemon4)) {
                    min = Config.getInstance().getConfig().getNode("pixelhunt", "general", "rare-pokemon-timer-min").getInt();
                    max = Config.getInstance().getConfig().getNode("pixelhunt", "general", "rare-pokemon-timer-max").getInt();
                }
                hours = random.nextInt(max - min + 1) + min;
                if (min == max) hours = max;
                date = date.plusHours(hours);
                PixelHuntForge.pokemon4expiry = date;

                huntTimer4 = Task.builder()
                        .execute(task -> {
                            sendServer(prefix() + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "hunt-ended").getString().replace("<pokemon>", PixelHuntForge.pokemon4));
                            randomisePokemon(slot);
                            sendServer(prefix() + Config.getInstance().getConfig().getNode("pixelhunt", "lang", "new-hunt").getString().replace("<pokemon>", PixelHuntForge.pokemon4));
                        })
                        .delay((hours * 3600L) * 20L)
                        .build();
                break;
        }
    }

    private void cancelTask (Task task){
        if(task != null){
            task.setExpired();
        }
    }

    public void cancelTimers(int slot) {
        if (slot == 0) {
            cancelTask(huntTimer1);
            cancelTask(huntTimer2);
            cancelTask(huntTimer3);
            cancelTask(huntTimer4);
        } else {
            switch (slot){
                case 1:
                    cancelTask(huntTimer1);
                    break;
                case 2:
                    cancelTask(huntTimer2);
                    break;
                case 3:
                    cancelTask(huntTimer3);
                    break;
                case 4:
                    cancelTask(huntTimer4);
                    break;
            }
        }
    }

    public static void sendServer(String string) {
        server.getPlayerList().sendMessage(new TextComponentString(regex(string)));
    }

    public static void sendPlayer(EntityPlayerMP player, String string) {
        player.sendMessage(new TextComponentString(regex(string)));
    }

    public int getRarity(String name) {
        if (getUncommonPokemon().contains(name)) {
            return 1;
        } else if (getRarePokemon().contains(name)) {
            return 2;
        } else {
            return 0;
        }
    }

    public void initialisePokemon() {
        cancelTimers(0);
        if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
            PixelHuntForge.pokemon1 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
        else PixelHuntForge.pokemon1 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
        while (getExcludedPokemon().contains(PixelHuntForge.pokemon1)) {
            if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                PixelHuntForge.pokemon1 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
            else PixelHuntForge.pokemon1 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
        }
        PixelHuntForge.nature1 = randomEnum(EnumNature.class).toString();
        PixelHuntForge.nature1b = randomEnum(EnumNature.class).toString();
        while (PixelHuntForge.nature1b.equalsIgnoreCase(PixelHuntForge.nature1))
            PixelHuntForge.nature1b = randomEnum(EnumNature.class).toString();
        PixelHuntForge.nature1c = randomEnum(EnumNature.class).toString();
        while (PixelHuntForge.nature1c.equalsIgnoreCase(PixelHuntForge.nature1b) || PixelHuntForge.nature1c.equalsIgnoreCase(PixelHuntForge.nature1))
            PixelHuntForge.nature1c = randomEnum(EnumNature.class).toString();
        PixelHuntForge.pokemon1ballReward = randomBall(1, 0);
        PixelHuntForge.pokemon1moneyReward = randomMoney(0);
        PixelHuntForge.pokemon1msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-common-msg").getString();
        if (getUncommonPokemon().contains(PixelHuntForge.pokemon1)) {
            PixelHuntForge.pokemon1moneyReward = randomMoney(1);
            PixelHuntForge.pokemon1rc = randomRareCandy(1);
            PixelHuntForge.pokemon1msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-uncommon-msg").getString();
        } else if (Utils.getRarePokemon().contains(PixelHuntForge.pokemon1)) {
            PixelHuntForge.pokemon1moneyReward = randomMoney(2);
            PixelHuntForge.pokemon1rc = randomRareCandy(2);
            PixelHuntForge.pokemon1msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-rare-msg").getString();
        } else PixelHuntForge.pokemon1rc = randomRareCandy(0);
        startTimer(1);
        if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
            pokemon2 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
        else pokemon2 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
        while (getExcludedPokemon().contains(pokemon2)) {
            if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                pokemon2 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
            else pokemon2 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
        }
        PixelHuntForge.nature2 = randomEnum(EnumNature.class).toString();
        PixelHuntForge.nature2b = randomEnum(EnumNature.class).toString();
        while (PixelHuntForge.nature2b.equalsIgnoreCase(PixelHuntForge.nature2))
            PixelHuntForge.nature2b = randomEnum(EnumNature.class).toString();
        PixelHuntForge.nature2c = randomEnum(EnumNature.class).toString();
        while (PixelHuntForge.nature2c.equalsIgnoreCase(PixelHuntForge.nature2b) || PixelHuntForge.nature2c.equalsIgnoreCase(PixelHuntForge.nature2))
            PixelHuntForge.nature2c = randomEnum(EnumNature.class).toString();
        PixelHuntForge.pokemon2ballReward = randomBall(2, 0);
        PixelHuntForge.pokemon2moneyReward = randomMoney(0);
        PixelHuntForge.pokemon2msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-common-msg").getString();
        if (getUncommonPokemon().contains(pokemon2)) {
            PixelHuntForge.pokemon2moneyReward = randomMoney(1);
            PixelHuntForge.pokemon2rc = randomRareCandy(1);
            PixelHuntForge.pokemon2msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-uncommon-msg").getString();
        } else if (Utils.getRarePokemon().contains(pokemon2)) {
            PixelHuntForge.pokemon2moneyReward = randomMoney(2);
            PixelHuntForge.pokemon2rc = randomRareCandy(2);
            PixelHuntForge.pokemon2msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-rare-msg").getString();
        } else PixelHuntForge.pokemon2rc = randomRareCandy(0);
        startTimer(2);
        if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
            PixelHuntForge.pokemon3 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
        else PixelHuntForge.pokemon3 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
        while (getExcludedPokemon().contains(PixelHuntForge.pokemon3)) {
            if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                PixelHuntForge.pokemon3 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
            else PixelHuntForge.pokemon3 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
        }
        PixelHuntForge.nature3 = randomEnum(EnumNature.class).toString();
        PixelHuntForge.nature3b = randomEnum(EnumNature.class).toString();
        while (PixelHuntForge.nature3b.equalsIgnoreCase(PixelHuntForge.nature3))
            PixelHuntForge.nature3b = randomEnum(EnumNature.class).toString();
        PixelHuntForge.nature3c = randomEnum(EnumNature.class).toString();
        while (PixelHuntForge.nature3c.equalsIgnoreCase(PixelHuntForge.nature3b) || PixelHuntForge.nature3c.equalsIgnoreCase(PixelHuntForge.nature3))
            PixelHuntForge.nature3c = randomEnum(EnumNature.class).toString();
        PixelHuntForge.pokemon3ballReward = randomBall(3, 0);
        PixelHuntForge.pokemon3moneyReward = randomMoney(0);
        PixelHuntForge.pokemon3msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-common-msg").getString();
        if (getUncommonPokemon().contains(PixelHuntForge.pokemon3)) {
            PixelHuntForge.pokemon3moneyReward = randomMoney(1);
            PixelHuntForge.pokemon3rc = randomRareCandy(1);
            PixelHuntForge.pokemon3msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-uncommon-msg").getString();
        } else if (Utils.getRarePokemon().contains(PixelHuntForge.pokemon3)) {
            PixelHuntForge.pokemon3moneyReward = randomMoney(2);
            PixelHuntForge.pokemon3rc = randomRareCandy(2);
            PixelHuntForge.pokemon3msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-rare-msg").getString();
        } else PixelHuntForge.pokemon3rc = randomRareCandy(0);
        startTimer(3);
        if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
            PixelHuntForge.pokemon4 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
        else PixelHuntForge.pokemon4 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
        while (getExcludedPokemon().contains(PixelHuntForge.pokemon4)) {
            if (Config.getInstance().getConfig().getNode("pixelhunt", "general", "allow-legendaries").getBoolean())
                PixelHuntForge.pokemon4 = sanitisePokemon(EnumSpecies.randomPoke(true).name);
            else PixelHuntForge.pokemon4 = sanitisePokemon(EnumSpecies.randomPoke(false).name);
        }
        PixelHuntForge.nature4 = randomEnum(EnumNature.class).toString();
        PixelHuntForge.nature4b = randomEnum(EnumNature.class).toString();
        while (PixelHuntForge.nature4b.equalsIgnoreCase(PixelHuntForge.nature4))
            PixelHuntForge.nature4b = randomEnum(EnumNature.class).toString();
        PixelHuntForge.nature4c = randomEnum(EnumNature.class).toString();
        while (PixelHuntForge.nature4c.equalsIgnoreCase(PixelHuntForge.nature4b) || PixelHuntForge.nature4c.equalsIgnoreCase(PixelHuntForge.nature4))
            PixelHuntForge.nature4c = randomEnum(EnumNature.class).toString();
        PixelHuntForge.pokemon4ballReward = randomBall(4, 0);
        PixelHuntForge.pokemon4moneyReward = randomMoney(0);
        PixelHuntForge.pokemon4msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-common-msg").getString();
        if (getUncommonPokemon().contains(PixelHuntForge.pokemon4)) {
            PixelHuntForge.pokemon4moneyReward = randomMoney(1);
            PixelHuntForge.pokemon4rc = randomRareCandy(1);
            PixelHuntForge.pokemon4msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-uncommon-msg").getString();
        } else if (Utils.getRarePokemon().contains(PixelHuntForge.pokemon4)) {
            PixelHuntForge.pokemon4moneyReward = randomMoney(2);
            PixelHuntForge.pokemon4rc = randomRareCandy(2);
            PixelHuntForge.pokemon4msg = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-rare-msg").getString();
        } else PixelHuntForge.pokemon4rc = randomRareCandy(0);
        startTimer(4);
    }

    public int randomMoney(int rarity) {
        Random rn = new Random();
        int min = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "common-money-min").getInt(), max = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "common-money-max").getInt();
        if (rarity == 1) {
            min = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "uncommon-money-min").getInt();
            max = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "uncommon-money-max").getInt();
        } else if (rarity == 2) {
            min = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "rare-money-min").getInt();
            max = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "rare-money-max").getInt();
        }
        return (rn.nextInt(max - min + 1) + min);
    }

    public void executeCommand(int rarity, String name) {
        List<String> cmd1list = new ArrayList<>();
        List<String> cmd2list = new ArrayList<>();
        List<String> cmd3list = new ArrayList<>();
        String cmd1 = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-common-cmd").getString().replace("%player%", name);
        String cmd2 = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-uncommon-cmd").getString().replace("%player%", name);
        String cmd3 = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "custom-rare-cmd").getString().replace("%player%", name);

        if (rarity == 1) {
            if (cmd2.contains(";")) cmd2list = Arrays.asList(cmd2.split("\\s*;\\s*"));
            else cmd2list.add(cmd2);

            for (String command : cmd2list) {
                server.getCommandManager().executeCommand(server, command);
            }

        } else if (rarity == 2) {
            if (cmd3.contains(";")) cmd3list = Arrays.asList(cmd3.split("\\s*;\\s*"));
            else cmd3list.add(cmd3);

            for (String command : cmd3list) {
                server.getCommandManager().executeCommand(server, command);
            }

        } else {
            if (cmd1.contains(";")) cmd1list = Arrays.asList(cmd1.split("\\s*;\\s*"));
            else cmd1list.add(cmd1);

            for (String command : cmd1list) {
                server.getCommandManager().executeCommand(server, command);
            }

        }
    }

    public ItemStack randomRareCandy(int rarity) {
        Random rn = new Random();
        int min = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "common-rarecandy-min").getInt(), max = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "common-rarecandy-max").getInt();
        if (rarity == 1) {
            min = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "uncommon-rarecandy-min").getInt();
            max = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "uncommon-rarecandy-max").getInt();
        } else if (rarity == 2) {
            min = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "rare-rarecandy-min").getInt();
            max = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "rare-rarecandy-max").getInt();
        }

        ItemStack is = new ItemStack(PixelmonItems.rareCandy);
        is.setCount(rn.nextInt(max - min + 1) + min);
        return is;
    }

    public ItemStack randomBall(int slot, int rarity) {
        String name = randomEnum(EnumItems.class).toString();
        switch (slot) {
            case 1:
                switch (name) {
                    case "level_ball":
                        PixelHuntForge.pokemon1ballName = "Level Balls";
                        break;
                    case "moon_ball":
                        PixelHuntForge.pokemon1ballName = "Moon Balls";
                        break;
                    case "friend_ball":
                        PixelHuntForge.pokemon1ballName = "Friend Balls";
                        break;
                    case "love_ball":
                        PixelHuntForge.pokemon1ballName = "Love Balls";
                        break;
                    case "timer_ball":
                        PixelHuntForge.pokemon1ballName = "Timer Balls";
                        break;
                    case "nest_ball":
                        PixelHuntForge.pokemon1ballName = "Nest Balls";
                        break;
                    case "dive_ball":
                        PixelHuntForge.pokemon1ballName = "Dive Balls";
                        break;
                    case "luxury_ball":
                        PixelHuntForge.pokemon1ballName = "Luxury Balls";
                        break;
                    case "heal_ball":
                        PixelHuntForge.pokemon1ballName = "Heal Balls";
                        break;
                    case "dusk_ball":
                        PixelHuntForge.pokemon1ballName = "Dusk Balls";
                        break;
                    case "lure_ball":
                        PixelHuntForge.pokemon1ballName = "Lure Balls";
                        break;
                    case "sport_ball":
                        PixelHuntForge.pokemon1ballName = "Sport Balls";
                        break;
                    case "ultra_ball":
                        PixelHuntForge.pokemon1ballName = "Ultra Balls";
                        break;
                    case "poke_ball":
                        PixelHuntForge.pokemon1ballName = "Poke Balls";
                        break;
                    case "quick_ball":
                        PixelHuntForge.pokemon1ballName = "Quick Balls";
                        break;
                    case "heavy_ball":
                        PixelHuntForge.pokemon1ballName = "Heavy Balls";
                        break;
                    case "fast_ball":
                        PixelHuntForge.pokemon1ballName = "Fast Balls";
                        break;
                    case "repeat_ball":
                        PixelHuntForge.pokemon1ballName = "Repeat Balls";
                        break;
                    case "gs_ball":
                        PixelHuntForge.pokemon1ballName = "GS Balls";
                        break;
                    case "great_ball":
                        PixelHuntForge.pokemon1ballName = "Great Balls";
                        break;
                    case "master_ball":
                        PixelHuntForge.pokemon1ballName = "Master Balls";
                        break;
                    case "park_ball":
                        PixelHuntForge.pokemon1ballName = "Park Balls";
                        break;
                    default:
                        PixelHuntForge.pokemon1ballName = "??? Balls";
                        break;
                }
                break;
            case 2:
                switch (name) {
                    case "level_ball":
                        PixelHuntForge.pokemon2ballName = "Level Balls";
                        break;
                    case "moon_ball":
                        PixelHuntForge.pokemon2ballName = "Moon Balls";
                        break;
                    case "friend_ball":
                        PixelHuntForge.pokemon2ballName = "Friend Balls";
                        break;
                    case "love_ball":
                        PixelHuntForge.pokemon2ballName = "Love Balls";
                        break;
                    case "timer_ball":
                        PixelHuntForge.pokemon2ballName = "Timer Balls";
                        break;
                    case "nest_ball":
                        PixelHuntForge.pokemon2ballName = "Nest Balls";
                        break;
                    case "dive_ball":
                        PixelHuntForge.pokemon2ballName = "Dive Balls";
                        break;
                    case "luxury_ball":
                        PixelHuntForge.pokemon2ballName = "Luxury Balls";
                        break;
                    case "heal_ball":
                        PixelHuntForge.pokemon2ballName = "Heal Balls";
                        break;
                    case "dusk_ball":
                        PixelHuntForge.pokemon2ballName = "Dusk Balls";
                        break;
                    case "lure_ball":
                        PixelHuntForge.pokemon2ballName = "Lure Balls";
                        break;
                    case "sport_ball":
                        PixelHuntForge.pokemon2ballName = "Sport Balls";
                        break;
                    case "ultra_ball":
                        PixelHuntForge.pokemon2ballName = "Ultra Balls";
                        break;
                    case "poke_ball":
                        PixelHuntForge.pokemon2ballName = "Poke Balls";
                        break;
                    case "quick_ball":
                        PixelHuntForge.pokemon2ballName = "Quick Balls";
                        break;
                    case "heavy_ball":
                        PixelHuntForge.pokemon2ballName = "Heavy Balls";
                        break;
                    case "fast_ball":
                        PixelHuntForge.pokemon2ballName = "Fast Balls";
                        break;
                    case "repeat_ball":
                        PixelHuntForge.pokemon2ballName = "Repeat Balls";
                        break;
                    case "gs_ball":
                        PixelHuntForge.pokemon2ballName = "GS Balls";
                        break;
                    case "great_ball":
                        PixelHuntForge.pokemon2ballName = "Great Balls";
                        break;
                    case "master_ball":
                        PixelHuntForge.pokemon2ballName = "Master Balls";
                        break;
                    case "park_ball":
                        PixelHuntForge.pokemon2ballName = "Park Balls";
                        break;
                    default:
                        PixelHuntForge.pokemon2ballName = "??? Balls";
                        break;
                }
                break;
            case 3:
                switch (name) {
                    case "level_ball":
                        PixelHuntForge.pokemon3ballName = "Level Balls";
                        break;
                    case "moon_ball":
                        PixelHuntForge.pokemon3ballName = "Moon Balls";
                        break;
                    case "friend_ball":
                        PixelHuntForge.pokemon3ballName = "Friend Balls";
                        break;
                    case "love_ball":
                        PixelHuntForge.pokemon3ballName = "Love Balls";
                        break;
                    case "timer_ball":
                        PixelHuntForge.pokemon3ballName = "Timer Balls";
                        break;
                    case "nest_ball":
                        PixelHuntForge.pokemon3ballName = "Nest Balls";
                        break;
                    case "dive_ball":
                        PixelHuntForge.pokemon3ballName = "Dive Balls";
                        break;
                    case "luxury_ball":
                        PixelHuntForge.pokemon3ballName = "Luxury Balls";
                        break;
                    case "heal_ball":
                        PixelHuntForge.pokemon3ballName = "Heal Balls";
                        break;
                    case "dusk_ball":
                        PixelHuntForge.pokemon3ballName = "Dusk Balls";
                        break;
                    case "lure_ball":
                        PixelHuntForge.pokemon3ballName = "Lure Balls";
                        break;
                    case "sport_ball":
                        PixelHuntForge.pokemon3ballName = "Sport Balls";
                        break;
                    case "ultra_ball":
                        PixelHuntForge.pokemon3ballName = "Ultra Balls";
                        break;
                    case "poke_ball":
                        PixelHuntForge.pokemon3ballName = "Poke Balls";
                        break;
                    case "quick_ball":
                        PixelHuntForge.pokemon3ballName = "Quick Balls";
                        break;
                    case "heavy_ball":
                        PixelHuntForge.pokemon3ballName = "Heavy Balls";
                        break;
                    case "fast_ball":
                        PixelHuntForge.pokemon3ballName = "Fast Balls";
                        break;
                    case "repeat_ball":
                        PixelHuntForge.pokemon3ballName = "Repeat Balls";
                        break;
                    case "gs_ball":
                        PixelHuntForge.pokemon3ballName = "GS Balls";
                        break;
                    case "great_ball":
                        PixelHuntForge.pokemon3ballName = "Great Balls";
                        break;
                    case "master_ball":
                        PixelHuntForge.pokemon3ballName = "Master Balls";
                        break;
                    case "park_ball":
                        PixelHuntForge.pokemon3ballName = "Park Balls";
                        break;
                    default:
                        PixelHuntForge.pokemon3ballName = "??? Balls";
                        break;
                }
                break;
            case 4:
                switch (name) {
                    case "level_ball":
                        PixelHuntForge.pokemon4ballName = "Level Balls";
                        break;
                    case "moon_ball":
                        PixelHuntForge.pokemon4ballName = "Moon Balls";
                        break;
                    case "friend_ball":
                        PixelHuntForge.pokemon4ballName = "Friend Balls";
                        break;
                    case "love_ball":
                        PixelHuntForge.pokemon4ballName = "Love Balls";
                        break;
                    case "timer_ball":
                        PixelHuntForge.pokemon4ballName = "Timer Balls";
                        break;
                    case "nest_ball":
                        PixelHuntForge.pokemon4ballName = "Nest Balls";
                        break;
                    case "dive_ball":
                        PixelHuntForge.pokemon4ballName = "Dive Balls";
                        break;
                    case "luxury_ball":
                        PixelHuntForge.pokemon4ballName = "Luxury Balls";
                        break;
                    case "heal_ball":
                        PixelHuntForge.pokemon4ballName = "Heal Balls";
                        break;
                    case "dusk_ball":
                        PixelHuntForge.pokemon4ballName = "Dusk Balls";
                        break;
                    case "lure_ball":
                        PixelHuntForge.pokemon4ballName = "Lure Balls";
                        break;
                    case "sport_ball":
                        PixelHuntForge.pokemon4ballName = "Sport Balls";
                        break;
                    case "ultra_ball":
                        PixelHuntForge.pokemon4ballName = "Ultra Balls";
                        break;
                    case "poke_ball":
                        PixelHuntForge.pokemon4ballName = "Poke Balls";
                        break;
                    case "quick_ball":
                        PixelHuntForge.pokemon4ballName = "Quick Balls";
                        break;
                    case "heavy_ball":
                        PixelHuntForge.pokemon4ballName = "Heavy Balls";
                        break;
                    case "fast_ball":
                        PixelHuntForge.pokemon4ballName = "Fast Balls";
                        break;
                    case "repeat_ball":
                        PixelHuntForge.pokemon4ballName = "Repeat Balls";
                        break;
                    case "gs_ball":
                        PixelHuntForge.pokemon4ballName = "GS Balls";
                        break;
                    case "great_ball":
                        PixelHuntForge.pokemon4ballName = "Great Balls";
                        break;
                    case "master_ball":
                        PixelHuntForge.pokemon4ballName = "Master Balls";
                        break;
                    case "park_ball":
                        PixelHuntForge.pokemon4ballName = "Park Balls";
                        break;
                    default:
                        PixelHuntForge.pokemon4ballName = "??? Balls";
                        break;
                }
                break;
        }

        Random rn = new Random();
        int min = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "common-balls-min").getInt(), max = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "common-balls-max").getInt();
        if (rarity == 1) {
            min = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "uncommon-balls-min").getInt();
            max = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "uncommon-balls-max").getInt();
        } else if (rarity == 2) {
            min = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "rare-balls-min").getInt();
            max = Config.getInstance().getConfig().getNode("pixelhunt", "rewards", "rare-balls-max").getInt();
        }

        ItemStack is = new ItemStack(Item.getByNameOrId("pixelmon:" + name));
        is.setCount(rn.nextInt(max - min + 1) + min);
        return is;
    }


    // Takes a config String, and replaces a single placeholder with the proper replacement as many times as needed.
    public static String replacePlaceholder(final String input, final String placeholder, final String replacement) {
        // If our input has a placeholder inside, replace it with the provided replacement String. Case-insensitive.
        if (input.toLowerCase().contains(placeholder))
            return input.replaceAll("(?i)" + placeholder, replacement);
        else
            return input;
    }

    public static List<String> getExcludedPokemon() {
        List<String> items = Arrays.asList(Config.getInstance().getConfig().getNode("pixelhunt", "lists", "excluded-pokemon-list").getString().split("\\s*,\\s*"));
        return items;
    }

    public static List<String> getUncommonPokemon() {
        List<String> items = Arrays.asList(Config.getInstance().getConfig().getNode("pixelhunt", "lists", "uncommon-pokemon-list").getString().split("\\s*,\\s*"));
        return items;
    }

    public static List<String> getRarePokemon() {
        List<String> items = Arrays.asList(Config.getInstance().getConfig().getNode("pixelhunt", "lists", "rare-pokemon-list").getString().split("\\s*,\\s*"));
        return items;
    }

    public static String prefix() {
        return regex(Config.getInstance().getConfig().getNode("pixelhunt", "lang", "prefix").getString());
    }

    public static String lang(String node) {
        return regex(Config.getInstance().getConfig().getNode("pixelhunt", "lang", node).getString());
    }
}
