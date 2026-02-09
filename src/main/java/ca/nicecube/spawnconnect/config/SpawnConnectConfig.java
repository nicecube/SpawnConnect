package ca.nicecube.spawnconnect.config;

import com.google.gson.annotations.SerializedName;

public class SpawnConnectConfig {
    @SerializedName(value = "overrideSpawn", alternate = {"overdirespawn"})
    private boolean overrideSpawn = false;
    private OverrideCoordinates coordinates = new OverrideCoordinates();

    public boolean isOverrideSpawn() {
        return overrideSpawn;
    }

    public OverrideCoordinates getCoordinates() {
        return coordinates;
    }

    public static SpawnConnectConfig defaults() {
        return new SpawnConnectConfig();
    }

    public void normalize() {
        if (this.coordinates == null) {
            this.coordinates = new OverrideCoordinates();
        }

        this.coordinates.normalize();
    }

    public static class OverrideCoordinates {
        @SerializedName(value = "world", alternate = {"universe"})
        private String world = "default";
        private double x = 0.0d;
        private double y = 0.0d;
        private double z = 0.0d;

        public String getWorld() {
            return world;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }

        public void normalize() {
            if (this.world == null || this.world.isBlank()) {
                this.world = "default";
            }
        }
    }
}
