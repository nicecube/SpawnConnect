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
    }

    public static class OverrideCoordinates {
        private double x = 0.0d;
        private double y = 0.0d;
        private double z = 0.0d;

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }
    }
}
