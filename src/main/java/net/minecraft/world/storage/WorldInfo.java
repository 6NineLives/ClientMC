package net.minecraft.world.storage;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

import net.lax1dude.eaglercraft.v1_8.HString;

/**+
 * This portion of EaglercraftX contains deobfuscated Minecraft 1.8 source code.
 * 
 * Minecraft 1.8.8 bytecode is (c) 2015 Mojang AB. "Do not distribute!"
 * Mod Coder Pack v9.18 deobfuscation configs are (c) Copyright by the MCP Team
 * 
 * EaglercraftX 1.8 patch files (c) 2022-2024 lax1dude, ayunami2000. All Rights Reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 */
public class WorldInfo {
	public static final EnumDifficulty DEFAULT_DIFFICULTY = EnumDifficulty.NORMAL;
	private long randomSeed;
	private WorldType terrainType = WorldType.DEFAULT;
	private String generatorOptions = "";
	private int spawnX;
	private int spawnY;
	private int spawnZ;
	private long totalTime;
	private long worldTime;
	private long lastTimePlayed;
	private long sizeOnDisk;
	private NBTTagCompound playerTag;
	private int dimension;
	private String levelName;
	private int saveVersion;
	private int cleanWeatherTime;
	private boolean raining;
	private int rainTime;
	private boolean thundering;
	private int thunderTime;
	private WorldSettings.GameType theGameType;
	private boolean mapFeaturesEnabled;
	private boolean hardcore;
	private boolean allowCommands;
	private boolean initialized;
	private EnumDifficulty difficulty;
	private boolean difficultyLocked;
	private double borderCenterX = 0.0D;
	private double borderCenterZ = 0.0D;
	private double borderSize = 6.0E7D;
	private long borderSizeLerpTime = 0L;
	private double borderSizeLerpTarget = 0.0D;
	private double borderSafeZone = 5.0D;
	private double borderDamagePerBlock = 0.2D;
	private int borderWarningDistance = 5;
	private int borderWarningTime = 15;
	private GameRules theGameRules = new GameRules();

	public static final int eaglerVersionCurrent = 1;
	private int eaglerVersion = eaglerVersionCurrent;

	protected WorldInfo() {
	}

	public WorldInfo(NBTTagCompound nbt) {
		this.randomSeed = nbt.getLong("RandomSeed");
		if (nbt.hasKey("generatorName", 8)) {
			String s = nbt.getString("generatorName");
			this.terrainType = WorldType.parseWorldType(s);
			if (this.terrainType == null) {
				this.terrainType = WorldType.DEFAULT;
			} else if (this.terrainType.isVersioned()) {
				int i = 0;
				if (nbt.hasKey("generatorVersion", 99)) {
					i = nbt.getInteger("generatorVersion");
				}

				this.terrainType = this.terrainType.getWorldTypeForGeneratorVersion(i);
			}

			if (nbt.hasKey("generatorOptions", 8)) {
				this.generatorOptions = nbt.getString("generatorOptions");
			}
		}

		this.theGameType = WorldSettings.GameType.getByID(nbt.getInteger("GameType"));
		if (nbt.hasKey("MapFeatures", 99)) {
			this.mapFeaturesEnabled = nbt.getBoolean("MapFeatures");
		} else {
			this.mapFeaturesEnabled = true;
		}

		this.spawnX = nbt.getInteger("SpawnX");
		this.spawnY = nbt.getInteger("SpawnY");
		this.spawnZ = nbt.getInteger("SpawnZ");
		this.totalTime = nbt.getLong("Time");
		if (nbt.hasKey("DayTime", 99)) {
			this.worldTime = nbt.getLong("DayTime");
		} else {
			this.worldTime = this.totalTime;
		}

		this.lastTimePlayed = nbt.getLong("LastPlayed");
		this.sizeOnDisk = nbt.getLong("SizeOnDisk");
		this.levelName = nbt.getString("LevelName");
		this.saveVersion = nbt.getInteger("version");
		this.cleanWeatherTime = nbt.getInteger("clearWeatherTime");
		this.rainTime = nbt.getInteger("rainTime");
		this.raining = nbt.getBoolean("raining");
		this.thunderTime = nbt.getInteger("thunderTime");
		this.thundering = nbt.getBoolean("thundering");
		this.hardcore = nbt.getBoolean("hardcore");
		if (nbt.hasKey("initialized", 99)) {
			this.initialized = nbt.getBoolean("initialized");
		} else {
			this.initialized = true;
		}

		if (nbt.hasKey("allowCommands", 99)) {
			this.allowCommands = nbt.getBoolean("allowCommands");
		} else {
			this.allowCommands = this.theGameType == WorldSettings.GameType.CREATIVE;
		}

		if (nbt.hasKey("Player", 10)) {
			this.playerTag = nbt.getCompoundTag("Player");
			this.dimension = this.playerTag.getInteger("Dimension");
		}

		if (nbt.hasKey("GameRules", 10)) {
			this.theGameRules.readFromNBT(nbt.getCompoundTag("GameRules"));
		}

		if (nbt.hasKey("Difficulty", 99)) {
			this.difficulty = EnumDifficulty.getDifficultyEnum(nbt.getByte("Difficulty"));
		}

		if (nbt.hasKey("DifficultyLocked", 1)) {
			this.difficultyLocked = nbt.getBoolean("DifficultyLocked");
		}

		if (nbt.hasKey("BorderCenterX", 99)) {
			this.borderCenterX = nbt.getDouble("BorderCenterX");
		}

		if (nbt.hasKey("BorderCenterZ", 99)) {
			this.borderCenterZ = nbt.getDouble("BorderCenterZ");
		}

		if (nbt.hasKey("BorderSize", 99)) {
			this.borderSize = nbt.getDouble("BorderSize");
		}

		if (nbt.hasKey("BorderSizeLerpTime", 99)) {
			this.borderSizeLerpTime = nbt.getLong("BorderSizeLerpTime");
		}

		if (nbt.hasKey("BorderSizeLerpTarget", 99)) {
			this.borderSizeLerpTarget = nbt.getDouble("BorderSizeLerpTarget");
		}

		if (nbt.hasKey("BorderSafeZone", 99)) {
			this.borderSafeZone = nbt.getDouble("BorderSafeZone");
		}

		if (nbt.hasKey("BorderDamagePerBlock", 99)) {
			this.borderDamagePerBlock = nbt.getDouble("BorderDamagePerBlock");
		}

		if (nbt.hasKey("BorderWarningBlocks", 99)) {
			this.borderWarningDistance = nbt.getInteger("BorderWarningBlocks");
		}

		if (nbt.hasKey("BorderWarningTime", 99)) {
			this.borderWarningTime = nbt.getInteger("BorderWarningTime");
		}

		this.eaglerVersion = nbt.getInteger("eaglerVersionSerial");
	}

	public WorldInfo(WorldSettings settings, String name) {
		this.populateFromWorldSettings(settings);
		this.levelName = name;
		this.difficulty = DEFAULT_DIFFICULTY;
		this.initialized = false;
	}

	public void populateFromWorldSettings(WorldSettings settings) {
		this.randomSeed = settings.getSeed();
		this.theGameType = settings.getGameType();
		this.mapFeaturesEnabled = settings.isMapFeaturesEnabled();
		this.hardcore = settings.getHardcoreEnabled();
		this.terrainType = settings.getTerrainType();
		this.generatorOptions = settings.getWorldName();
		this.allowCommands = settings.areCommandsAllowed();
	}

	public WorldInfo(WorldInfo worldInformation) {
		this.randomSeed = worldInformation.randomSeed;
		this.terrainType = worldInformation.terrainType;
		this.generatorOptions = worldInformation.generatorOptions;
		this.theGameType = worldInformation.theGameType;
		this.mapFeaturesEnabled = worldInformation.mapFeaturesEnabled;
		this.spawnX = worldInformation.spawnX;
		this.spawnY = worldInformation.spawnY;
		this.spawnZ = worldInformation.spawnZ;
		this.totalTime = worldInformation.totalTime;
		this.worldTime = worldInformation.worldTime;
		this.lastTimePlayed = worldInformation.lastTimePlayed;
		this.sizeOnDisk = worldInformation.sizeOnDisk;
		this.playerTag = worldInformation.playerTag;
		this.dimension = worldInformation.dimension;
		this.levelName = worldInformation.levelName;
		this.saveVersion = worldInformation.saveVersion;
		this.rainTime = worldInformation.rainTime;
		this.raining = worldInformation.raining;
		this.thunderTime = worldInformation.thunderTime;
		this.thundering = worldInformation.thundering;
		this.hardcore = worldInformation.hardcore;
		this.allowCommands = worldInformation.allowCommands;
		this.initialized = worldInformation.initialized;
		this.theGameRules = worldInformation.theGameRules;
		this.difficulty = worldInformation.difficulty;
		this.difficultyLocked = worldInformation.difficultyLocked;
		this.borderCenterX = worldInformation.borderCenterX;
		this.borderCenterZ = worldInformation.borderCenterZ;
		this.borderSize = worldInformation.borderSize;
		this.borderSizeLerpTime = worldInformation.borderSizeLerpTime;
		this.borderSizeLerpTarget = worldInformation.borderSizeLerpTarget;
		this.borderSafeZone = worldInformation.borderSafeZone;
		this.borderDamagePerBlock = worldInformation.borderDamagePerBlock;
		this.borderWarningTime = worldInformation.borderWarningTime;
		this.borderWarningDistance = worldInformation.borderWarningDistance;
	}

	/**+
	 * Gets the NBTTagCompound for the worldInfo
	 */
	public NBTTagCompound getNBTTagCompound() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.updateTagCompound(nbttagcompound, this.playerTag);
		return nbttagcompound;
	}

	/**+
	 * Creates a new NBTTagCompound for the world, with the given
	 * NBTTag as the "Player"
	 */
	public NBTTagCompound cloneNBTCompound(NBTTagCompound nbttagcompound) {
		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
		this.updateTagCompound(nbttagcompound1, nbttagcompound);
		return nbttagcompound1;
	}

	private void updateTagCompound(NBTTagCompound nbt, NBTTagCompound playerNbt) {
		nbt.setLong("RandomSeed", this.randomSeed);
		nbt.setString("generatorName", this.terrainType.getWorldTypeName());
		nbt.setInteger("generatorVersion", this.terrainType.getGeneratorVersion());
		nbt.setString("generatorOptions", this.generatorOptions);
		nbt.setInteger("GameType", this.theGameType.getID());
		nbt.setBoolean("MapFeatures", this.mapFeaturesEnabled);
		nbt.setInteger("SpawnX", this.spawnX);
		nbt.setInteger("SpawnY", this.spawnY);
		nbt.setInteger("SpawnZ", this.spawnZ);
		nbt.setLong("Time", this.totalTime);
		nbt.setLong("DayTime", this.worldTime);
		nbt.setLong("SizeOnDisk", this.sizeOnDisk);
		nbt.setLong("LastPlayed", MinecraftServer.getCurrentTimeMillis());
		nbt.setString("LevelName", this.levelName);
		nbt.setInteger("version", this.saveVersion);
		nbt.setInteger("clearWeatherTime", this.cleanWeatherTime);
		nbt.setInteger("rainTime", this.rainTime);
		nbt.setBoolean("raining", this.raining);
		nbt.setInteger("thunderTime", this.thunderTime);
		nbt.setBoolean("thundering", this.thundering);
		nbt.setBoolean("hardcore", this.hardcore);
		nbt.setBoolean("allowCommands", this.allowCommands);
		nbt.setBoolean("initialized", this.initialized);
		nbt.setDouble("BorderCenterX", this.borderCenterX);
		nbt.setDouble("BorderCenterZ", this.borderCenterZ);
		nbt.setDouble("BorderSize", this.borderSize);
		nbt.setLong("BorderSizeLerpTime", this.borderSizeLerpTime);
		nbt.setDouble("BorderSafeZone", this.borderSafeZone);
		nbt.setDouble("BorderDamagePerBlock", this.borderDamagePerBlock);
		nbt.setDouble("BorderSizeLerpTarget", this.borderSizeLerpTarget);
		nbt.setDouble("BorderWarningBlocks", (double) this.borderWarningDistance);
		nbt.setDouble("BorderWarningTime", (double) this.borderWarningTime);
		nbt.setInteger("eaglerVersionSerial", this.eaglerVersion);
		if (this.difficulty != null) {
			nbt.setByte("Difficulty", (byte) this.difficulty.getDifficultyId());
		}

		nbt.setBoolean("DifficultyLocked", this.difficultyLocked);
		nbt.setTag("GameRules", this.theGameRules.writeToNBT());
		if (playerNbt != null) {
			nbt.setTag("Player", playerNbt);
		}

	}

	/**+
	 * Returns the seed of current world.
	 */
	public long getSeed() {
		return this.randomSeed;
	}

	/**+
	 * Returns the x spawn position
	 */
	public int getSpawnX() {
		return this.spawnX;
	}

	/**+
	 * Return the Y axis spawning point of the player.
	 */
	public int getSpawnY() {
		return this.spawnY;
	}

	/**+
	 * Returns the z spawn position
	 */
	public int getSpawnZ() {
		return this.spawnZ;
	}

	public long getWorldTotalTime() {
		return this.totalTime;
	}

	/**+
	 * Get current world time
	 */
	public long getWorldTime() {
		return this.worldTime;
	}

	public long getSizeOnDisk() {
		return this.sizeOnDisk;
	}

	/**+
	 * Returns the player's NBTTagCompound to be loaded
	 */
	public NBTTagCompound getPlayerNBTTagCompound() {
		return this.playerTag;
	}

	/**+
	 * Set the x spawn position to the passed in value
	 */
	public void setSpawnX(int i) {
		this.spawnX = i;
	}

	/**+
	 * Sets the y spawn position
	 */
	public void setSpawnY(int i) {
		this.spawnY = i;
	}

	/**+
	 * Set the z spawn position to the passed in value
	 */
	public void setSpawnZ(int i) {
		this.spawnZ = i;
	}

	public void setWorldTotalTime(long i) {
		this.totalTime = i;
	}

	/**+
	 * Set current world time
	 */
	public void setWorldTime(long i) {
		this.worldTime = i;
	}

	public void setSpawn(BlockPos blockpos) {
		this.spawnX = blockpos.getX();
		this.spawnY = blockpos.getY();
		this.spawnZ = blockpos.getZ();
	}

	/**+
	 * Get current world name
	 */
	public String getWorldName() {
		return this.levelName;
	}

	public void setWorldName(String s) {
		this.levelName = s;
	}

	/**+
	 * Returns the save version of this world
	 */
	public int getSaveVersion() {
		return this.saveVersion;
	}

	/**+
	 * Sets the save version of the world
	 */
	public void setSaveVersion(int i) {
		this.saveVersion = i;
	}

	/**+
	 * Return the last time the player was in this world.
	 */
	public long getLastTimePlayed() {
		return this.lastTimePlayed;
	}

	public int getCleanWeatherTime() {
		return this.cleanWeatherTime;
	}

	public void setCleanWeatherTime(int cleanWeatherTimeIn) {
		this.cleanWeatherTime = cleanWeatherTimeIn;
	}

	/**+
	 * Returns true if it is thundering, false otherwise.
	 */
	public boolean isThundering() {
		return this.thundering;
	}

	/**+
	 * Sets whether it is thundering or not.
	 */
	public void setThundering(boolean flag) {
		this.thundering = flag;
	}

	/**+
	 * Returns the number of ticks until next thunderbolt.
	 */
	public int getThunderTime() {
		return this.thunderTime;
	}

	/**+
	 * Defines the number of ticks until next thunderbolt.
	 */
	public void setThunderTime(int i) {
		this.thunderTime = i;
	}

	/**+
	 * Returns true if it is raining, false otherwise.
	 */
	public boolean isRaining() {
		return this.raining;
	}

	/**+
	 * Sets whether it is raining or not.
	 */
	public void setRaining(boolean flag) {
		this.raining = flag;
	}

	/**+
	 * Return the number of ticks until rain.
	 */
	public int getRainTime() {
		return this.rainTime;
	}

	/**+
	 * Sets the number of ticks until rain.
	 */
	public void setRainTime(int i) {
		this.rainTime = i;
	}

	/**+
	 * Gets the GameType.
	 */
	public WorldSettings.GameType getGameType() {
		return this.theGameType;
	}

	/**+
	 * Get whether the map features (e.g. strongholds) generation is
	 * enabled or disabled.
	 */
	public boolean isMapFeaturesEnabled() {
		return this.mapFeaturesEnabled;
	}

	public void setMapFeaturesEnabled(boolean enabled) {
		this.mapFeaturesEnabled = enabled;
	}

	/**+
	 * Sets the GameType.
	 */
	public void setGameType(WorldSettings.GameType type) {
		this.theGameType = type;
	}

	/**+
	 * Returns true if hardcore mode is enabled, otherwise false
	 */
	public boolean isHardcoreModeEnabled() {
		return this.hardcore;
	}

	public void setHardcore(boolean hardcoreIn) {
		this.hardcore = hardcoreIn;
	}

	public WorldType getTerrainType() {
		return this.terrainType;
	}

	public void setTerrainType(WorldType worldtype) {
		this.terrainType = worldtype;
	}

	public String getGeneratorOptions() {
		return this.generatorOptions;
	}

	/**+
	 * Returns true if commands are allowed on this World.
	 */
	public boolean areCommandsAllowed() {
		return this.allowCommands;
	}

	public void setAllowCommands(boolean flag) {
		this.allowCommands = flag;
	}

	/**+
	 * Returns true if the World is initialized.
	 */
	public boolean isInitialized() {
		return this.initialized;
	}

	/**+
	 * Sets the initialization status of the World.
	 */
	public void setServerInitialized(boolean flag) {
		this.initialized = flag;
	}

	/**+
	 * Gets the GameRules class Instance.
	 */
	public GameRules getGameRulesInstance() {
		return this.theGameRules;
	}

	/**+
	 * Returns the border center X position
	 */
	public double getBorderCenterX() {
		return this.borderCenterX;
	}

	/**+
	 * Returns the border center Z position
	 */
	public double getBorderCenterZ() {
		return this.borderCenterZ;
	}

	public double getBorderSize() {
		return this.borderSize;
	}

	/**+
	 * Sets the border size
	 */
	public void setBorderSize(double size) {
		this.borderSize = size;
	}

	/**+
	 * Returns the border lerp time
	 */
	public long getBorderLerpTime() {
		return this.borderSizeLerpTime;
	}

	/**+
	 * Sets the border lerp time
	 */
	public void setBorderLerpTime(long time) {
		this.borderSizeLerpTime = time;
	}

	/**+
	 * Returns the border lerp target
	 */
	public double getBorderLerpTarget() {
		return this.borderSizeLerpTarget;
	}

	/**+
	 * Sets the border lerp target
	 */
	public void setBorderLerpTarget(double lerpSize) {
		this.borderSizeLerpTarget = lerpSize;
	}

	/**+
	 * Returns the border center Z position
	 */
	public void getBorderCenterZ(double posZ) {
		this.borderCenterZ = posZ;
	}

	/**+
	 * Returns the border center X position
	 */
	public void getBorderCenterX(double posX) {
		this.borderCenterX = posX;
	}

	/**+
	 * Returns the border safe zone
	 */
	public double getBorderSafeZone() {
		return this.borderSafeZone;
	}

	/**+
	 * Sets the border safe zone
	 */
	public void setBorderSafeZone(double amount) {
		this.borderSafeZone = amount;
	}

	/**+
	 * Returns the border damage per block
	 */
	public double getBorderDamagePerBlock() {
		return this.borderDamagePerBlock;
	}

	/**+
	 * Sets the border damage per block
	 */
	public void setBorderDamagePerBlock(double damage) {
		this.borderDamagePerBlock = damage;
	}

	/**+
	 * Returns the border warning distance
	 */
	public int getBorderWarningDistance() {
		return this.borderWarningDistance;
	}

	/**+
	 * Returns the border warning time
	 */
	public int getBorderWarningTime() {
		return this.borderWarningTime;
	}

	/**+
	 * Sets the border warning distance
	 */
	public void setBorderWarningDistance(int amountOfBlocks) {
		this.borderWarningDistance = amountOfBlocks;
	}

	/**+
	 * Sets the border warning time
	 */
	public void setBorderWarningTime(int ticks) {
		this.borderWarningTime = ticks;
	}

	public EnumDifficulty getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(EnumDifficulty enumdifficulty) {
		this.difficulty = enumdifficulty;
	}

	public boolean isDifficultyLocked() {
		return this.difficultyLocked;
	}

	public void setDifficultyLocked(boolean flag) {
		this.difficultyLocked = flag;
	}

	public int getEaglerVersion() {
		return this.eaglerVersion;
	}

	public boolean isOldEaglercraftRandom() {
		return this.eaglerVersion == 0;
	}

	public static void initEaglerVersion(NBTTagCompound compound) {
		if (!compound.hasKey("eaglerVersionSerial", 99)) {
			compound.setInteger("eaglerVersionSerial", eaglerVersionCurrent);
		}
	}

	/**+
	 * Adds this WorldInfo instance to the crash report.
	 */
	public void addToCrashReport(CrashReportCategory category) {
		category.addCrashSectionCallable("Level seed", new Callable<String>() {
			public String call() throws Exception {
				return String.valueOf(WorldInfo.this.getSeed());
			}
		});
		category.addCrashSectionCallable("Level generator", new Callable<String>() {
			public String call() throws Exception {
				return HString.format("ID %02d - %s, ver %d. Features enabled: %b",
						new Object[] { Integer.valueOf(WorldInfo.this.terrainType.getWorldTypeID()),
								WorldInfo.this.terrainType.getWorldTypeName(),
								Integer.valueOf(WorldInfo.this.terrainType.getGeneratorVersion()),
								Boolean.valueOf(WorldInfo.this.mapFeaturesEnabled) });
			}
		});
		category.addCrashSectionCallable("Level generator options", new Callable<String>() {
			public String call() throws Exception {
				return WorldInfo.this.generatorOptions;
			}
		});
		category.addCrashSectionCallable("Level spawn location", new Callable<String>() {
			public String call() throws Exception {
				return CrashReportCategory.getCoordinateInfo((double) WorldInfo.this.spawnX,
						(double) WorldInfo.this.spawnY, (double) WorldInfo.this.spawnZ);
			}
		});
		category.addCrashSectionCallable("Level time", new Callable<String>() {
			public String call() throws Exception {
				return HString.format("%d game time, %d day time", new Object[] {
						Long.valueOf(WorldInfo.this.totalTime), Long.valueOf(WorldInfo.this.worldTime) });
			}
		});
		category.addCrashSectionCallable("Level dimension", new Callable<String>() {
			public String call() throws Exception {
				return String.valueOf(WorldInfo.this.dimension);
			}
		});
		category.addCrashSectionCallable("Level storage version", new Callable<String>() {
			public String call() throws Exception {
				String s = "Unknown?";

				try {
					switch (WorldInfo.this.saveVersion) {
					case 19132:
						s = "McRegion";
						break;
					case 19133:
						s = "Anvil";
					}
				} catch (Throwable var3) {
					;
				}

				return HString.format("0x%05X - %s", new Object[] { Integer.valueOf(WorldInfo.this.saveVersion), s });
			}
		});
		category.addCrashSectionCallable("Level weather", new Callable<String>() {
			public String call() throws Exception {
				return HString.format("Rain time: %d (now: %b), thunder time: %d (now: %b)",
						new Object[] { Integer.valueOf(WorldInfo.this.rainTime),
								Boolean.valueOf(WorldInfo.this.raining), Integer.valueOf(WorldInfo.this.thunderTime),
								Boolean.valueOf(WorldInfo.this.thundering) });
			}
		});
		category.addCrashSectionCallable("Level game mode", new Callable<String>() {
			public String call() throws Exception {
				return HString.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", new Object[] {
						WorldInfo.this.theGameType.getName(), Integer.valueOf(WorldInfo.this.theGameType.getID()),
						Boolean.valueOf(WorldInfo.this.hardcore), Boolean.valueOf(WorldInfo.this.allowCommands) });
			}
		});
	}
}