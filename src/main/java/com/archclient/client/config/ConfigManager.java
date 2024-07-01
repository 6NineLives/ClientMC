package com.archclient.client.config;

import com.archclient.main.ArchClient;

import net.lax1dude.eaglercraft.v1_8.internal.vfs2.VFile2;

import com.archclient.client.module.AbstractModule;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ConfigManager {

    public static final VFile2 configDir;
    public static final VFile2 profilesDir;
    private static final VFile2 globalConfig;

    static {
        configDir = new VFile2("config", "client");
        profilesDir = new VFile2(configDir, "profiles");
        globalConfig = new VFile2(configDir, "global.cfg");
    }

    public void write() {
        try {
            if (this.createRequiredFiles()) {
                this.writeGlobalConfig(globalConfig);
                this.writeProfile(ArchClient.getInstance().activeProfile.getName());
            }
        } catch (Exception iOException) {
            iOException.printStackTrace();
        }
    }

    public void read() {
        try {
            if (this.createRequiredFiles()) {
                this.readGlobalConfig(globalConfig);
                if (ArchClient.getInstance().activeProfile == null) {
                    ArchClient.getInstance().activeProfile = ArchClient.getInstance().profiles.get(0);
                }
                this.readProfile(ArchClient.getInstance().activeProfile.getName());
            }
        } catch (Exception iOException) {
            iOException.printStackTrace();
        }
    }

    private boolean createRequiredFiles() throws Exception {
        return true;
    }

    public void readGlobalConfig(VFile2 file) {
        if (!file.exists()) {
            this.writeGlobalConfig(file);
            return;
        }
        try {
            String line;
            try (InputStream is = file.getInputStream()) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                while ((line = bufferedReader.readLine()) != null) {
                    try {
                        VFile2 profileFile;
                        String[] split;
                        if (line.startsWith("#") || line.length() == 0 || (split = line.split("=", 2)).length != 2)
                            continue;
                        if (split[0].equalsIgnoreCase("ProfileIndexes")) {
                            for (String declaration : split[1].split("]\\[")) {
                                declaration = declaration.replaceFirst("\\[", "");
                                String[] pair = declaration.split(",", 2);
                                try {
                                    int index = Integer.parseInt(pair[1]);
                                    for (Profile profile : ArchClient.getInstance().profiles) {
                                        if (index == 0 || !profile.getName().equalsIgnoreCase(pair[0])) continue;
                                        profile.setIndex(index);
                                    }
                                } catch (NumberFormatException numberFormatException) {
                                    // empty catch block
                                }
                            }
                            continue;
                        }
                        if (split[0].equalsIgnoreCase("ActiveProfile")) {
                            profileFile = null;
                            VFile2 profilesDir = new VFile2(configDir, "profiles");
                            profileFile = new VFile2(profilesDir, split[1] + ".cfg");
                            if (profileFile == null || !profileFile.exists()) continue;
                            Profile activeProfile = null;
                            for (Profile profile : ArchClient.getInstance().profiles) {
                                if (!split[1].equalsIgnoreCase((profile).getName())) continue;
                                activeProfile = profile;
                            }
                            if (activeProfile == null || !activeProfile.isEditable()) continue;
                            ArchClient.getInstance().activeProfile = activeProfile;
                            continue;
                        }
                        for (Setting setting : ArchClient.getInstance().globalSettings.settingsList) {
                            if (setting.getLabel().equalsIgnoreCase("label") || !setting.getLabel().equalsIgnoreCase(split[0]))
                                continue;
    
                            switch (setting.getType()) {
                                case BOOLEAN: {
                                    setting.setValue(Boolean.parseBoolean(split[1]));
                                    break;
                                }
                                case INTEGER: {
                                    if (split[1].contains("rainbow")) {
                                        setting.rainbow = true;
                                        int n = Integer.parseInt(split[1].split(";")[0]);
                                        if (n > (Integer) setting.getMinimumValue() || n < (Integer) setting.getMaximumValue())
                                            continue;
                                        setting.setValue(n);
                                        break;
                                    }
                                    setting.rainbow = false;
                                    int n = Integer.parseInt(split[1]);
                                    if (n > (Integer) setting.getMinimumValue() || n < (Integer) setting.getMaximumValue())
                                        continue;
                                    setting.setValue(n);
                                    break;
                                }
                                case FLOAT: {
                                    float f = Float.parseFloat(split[1]);
                                    if (!(f <= (Float) setting.getMinimumValue()) || !(f >= (Float) setting.getMaximumValue()))
                                        break;
                                    setting.setValue(f);
                                    break;
                                }
                                case DOUBLE: {
                                    double d = Double.parseDouble(split[1]);
                                    if (!(d <= (Double) setting.getMinimumValue()) || !(d >= (Double) setting.getMaximumValue()))
                                        break;
                                    setting.setValue(d);
                                    break;
                                }
                                case STRING_ARRAY: {
                                    boolean changed = false;
                                    for (Object value : setting.getAcceptedValues()) {
                                        if (!((String) value).equalsIgnoreCase(split[1])) continue;
                                        changed = true;
                                    }
                                    if (!changed) break;
                                    setting.setValue(split[1]);
                                }
                            }
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                bufferedReader.close();
            }
        } catch (Exception iOException) {
            iOException.printStackTrace();
        }
        this.writeGlobalConfig(file);
    }

    public void writeGlobalConfig(VFile2 file) {
        try (OutputStream os = file.getOutputStream()) {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
            bufferedWriter.write("################################");
            bufferedWriter.newLine();
            bufferedWriter.write("# MC_Client: GLOBAL SETTINGS");
            bufferedWriter.newLine();
            bufferedWriter.write("################################");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            if (ArchClient.getInstance().activeProfile != null && ArchClient.getInstance().activeProfile.isEditable()) {
                bufferedWriter.write("ActiveProfile=" + ArchClient.getInstance().activeProfile.getName());
                bufferedWriter.newLine();
            }
            for (Setting setting : ArchClient.getInstance().globalSettings.settingsList) {
                if (setting.getLabel().equalsIgnoreCase("label")) {
                    continue;
                }

                if (setting.rainbow) {
                    bufferedWriter.write(setting.getLabel() + "=" + setting.value() + ";rainbow");
                } else {
                    bufferedWriter.write(setting.getLabel() + "=" + setting.value());
                }

                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();
            bufferedWriter.write("ProfileIndexes=");
            for (Profile profile : ArchClient.getInstance().profiles) {
                bufferedWriter.write("[" + profile.getName() + "," + profile.getIndex() + "]");
            }
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception iOException) {
            iOException.printStackTrace();
        }
    }

    public void readProfile(String name) {
        if (name.equalsIgnoreCase("default")) {
            System.out.println("[AC] setting up default profile");
            for (AbstractModule module : ArchClient.getInstance().moduleManager.modules) {
                module.setState(module.defaultState);
                module.setAnchor(module.defaultGuiAnchor);
                module.setTranslations(module.defaultXTranslation, module.defaultYTranslation);
                module.setRenderHud(module.defaultRenderHud);
                for (int i = 0; i < module.getSettingsList().size(); ++i) {
                    try {
                        module.getSettingsList().get(i).setValue(module.getDefaultSettingsValues().get(i), false);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
            return;
        }
        VFile2 file = new VFile2(configDir, "profiles");
        VFile2 file2 = new VFile2(file, name + ".cfg");
        if (file2 == null || !file2.exists()) {
            this.writeProfile(name);
            return;
        }
        ArrayList<AbstractModule> arrayList = new ArrayList<>(ArchClient.getInstance().moduleManager.modules);
        try (InputStream is = file.getInputStream()) {
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            AbstractModule object = null;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    String[] split;
                    if (line.startsWith("#") || line.length() == 0) continue;
                    if (line.startsWith("[")) {
                        for (AbstractModule module : arrayList) {
                            if (!("[" + module.getName() + "]").equalsIgnoreCase(line)) continue;
                            object = module;
                        }
                        continue;
                    }
                    if (object == null) continue;
                    if (line.startsWith("-")) {
                        split = line.replaceFirst("-", "").split("=", 2);
                        if (split.length != 2) continue;
                        try {
                            switch (split[0]) {
                                case "State": {
                                    if (object.isStaffModule()) break;
                                    object.setState(Boolean.parseBoolean(split[1]));
                                    break;
                                }
                                case "RenderHUD": {
                                    object.setRenderHud(Boolean.parseBoolean(split[1]));
                                    break;
                                }
                                case "xTranslation": {
                                    object.setXTranslation(Float.parseFloat(split[1]));
                                    break;
                                }
                                case "yTranslation": {
                                    object.setYTranslation(Float.parseFloat(split[1]));
                                }
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        continue;
                    }
                    split = line.split("=", 2);
                    if (split.length != 2) continue;
                    for (Setting setting : object.getSettingsList()) {
                        if (setting.getLabel().equalsIgnoreCase("label") || !setting.getLabel().equalsIgnoreCase(split[0]))
                            continue;

                        switch (setting.getType()) {
                            case BOOLEAN: {
                                setting.setValue(Boolean.parseBoolean(split[1]));
                                break;
                            }
                            case INTEGER: {
                                if (split[1].contains("rainbow")) {
                                    Object[] arrobject = split[1].split(";");
                                    int n = Integer.parseInt((String) arrobject[0]);
                                    setting.rainbow = true;
                                    if (n > (Integer) setting.getMinimumValue() || n < (Integer) setting.getMaximumValue())
                                        continue;
                                    setting.setValue(n);
                                    break;
                                }
                                int n = Integer.parseInt(split[1]);
                                setting.rainbow = false;
                                if (n > (Integer) setting.getMinimumValue() || n < (Integer) setting.getMaximumValue())
                                    continue;
                                setting.setValue(n);
                                break;
                            }
                            case FLOAT: {
                                float f = Float.parseFloat(split[1]);
                                if (!(f <= (Float) setting.getMinimumValue()) || !(f >= (Float) setting.getMaximumValue()))
                                    break;
                                setting.setValue(f);
                                break;
                            }
                            case DOUBLE: {
                                double d = Double.parseDouble(split[1]);
                                if (!(d <= (Double) setting.getMinimumValue()) || !(d >= (Double) setting.getMaximumValue()))
                                    break;
                                setting.setValue(d);
                                break;
                            }
                            case STRING_ARRAY: {
                                boolean bl = false;
                                for (Object object3 : setting.getAcceptedValues()) {
                                    if (!((String) object3).equalsIgnoreCase(split[1])) continue;
                                    bl = true;
                                }
                                if (!bl) break;
                                setting.setValue(split[1]);
                                break;
                            }
                            case STRING: {
                                if (setting.getLabel().equalsIgnoreCase("label")) break;
                                setting.setValue(split[1].replaceAll("&([abcdefghijklmrABCDEFGHIJKLMNR0-9])|(&$)", "\u00a7$1"));
                            }
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            bufferedReader.close();
        } catch (Exception iOException) {
            iOException.printStackTrace();
        }
    }

    public void writeProfile(String string) {
        if (string.equalsIgnoreCase("default")) {
            return;
        }
        VFile2 profilesDir = new VFile2(configDir, "profiles");
        VFile2 profileFile = new VFile2(profilesDir, string + ".cfg");
        ArrayList<AbstractModule> arrayList = new ArrayList<>(ArchClient.getInstance().moduleManager.modules);
        try (OutputStream os = profileFile.getOutputStream()) {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
            bufferedWriter.write("################################");
            bufferedWriter.newLine();
            bufferedWriter.write("# MC_Client: MODULE SETTINGS");
            bufferedWriter.newLine();
            bufferedWriter.write("################################");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            for (AbstractModule Module : arrayList) {
                bufferedWriter.write("[" + Module.getName() + "]");
                bufferedWriter.newLine();
                bufferedWriter.write("-State=" + Module.isEnabled());
                bufferedWriter.newLine();
                bufferedWriter.write("-xTranslation=" + Module.getXTranslation());
                bufferedWriter.newLine();
                bufferedWriter.write("-yTranslation=" + Module.getYTranslation());
                bufferedWriter.newLine();
                bufferedWriter.write("-RenderHUD=" + Module.isRenderHud());
                bufferedWriter.newLine();
                for (Setting setting : Module.getSettingsList()) {
                    if (setting.getLabel().equalsIgnoreCase("label")) {
                        continue;
                    }
                    if (setting.getType() == Setting.Type.STRING) {
                        bufferedWriter.write(setting.getLabel() + "=" + (setting.value().toString())
                                .replaceAll("\u00a7", "&"));
                    } else if (setting.rainbow) {
                        bufferedWriter.write(setting.getLabel() + "=" + setting.value() + ";rainbow");
                    } else {
                        bufferedWriter.write(setting.getLabel() + "=" + setting.value());
                    }
                    bufferedWriter.newLine();
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception iOException) {
            iOException.printStackTrace();
        }
    }

}
