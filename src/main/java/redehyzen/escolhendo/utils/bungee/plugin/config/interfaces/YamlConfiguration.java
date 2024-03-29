package redehyzen.escolhendo.utils.bungee.plugin.config.interfaces;

import java.io.File;
import java.util.List;
import java.util.Set;

public abstract class YamlConfiguration {
  
  protected File file;
  
  public YamlConfiguration(File file) {
    this.file = file;
  }
  
  public abstract boolean createSection(String path);
  
  public abstract boolean set(String path, Object obj);
  
  public abstract boolean contains(String path);
  
  public abstract Object get(String path);
  
  public abstract int getInt(String path);
  
  public abstract int getInt(String path, int def);
  
  public abstract double getDouble(String path);
  
  public abstract double getDouble(String path, double def);
  
  public abstract String getString(String path);
  
  public abstract boolean getBoolean(String path);
  
  public abstract List<String> getStringList(String path);
  
  public abstract Set<String> getKeys(boolean flag);
  
  public abstract boolean reload();
  
  public abstract boolean save();
}
