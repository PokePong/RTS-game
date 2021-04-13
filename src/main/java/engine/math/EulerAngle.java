package engine.math;

import engine.util.Debug;
import org.joml.Vector3f;

public class EulerAngle {

    private float pitch;
    private float yaw;
    private float roll;


    public EulerAngle() {
        this(0, 0, 0);
    }

    public EulerAngle(EulerAngle euler) {
        this(euler.getPitch(), euler.getYaw(), euler.getRoll());
    }


    public EulerAngle(float pitch, float yaw) {
        this(pitch, yaw, 0);
    }

    public EulerAngle(float pitch, float yaw, float roll) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
        constrain();
    }

    public Vector3f forward() {

        Vector3f result = new Vector3f();

        //Convert the values to radians
        float yawTemp = (float) Math.toRadians(yaw);
        float pitchTemp = (float) Math.toRadians(pitch);

        //This will do the calculation to a vector
        result.x = (float) (Math.sin(yawTemp) * Math.cos(pitchTemp));
        result.y = (float) (Math.sin(pitchTemp));
        result.z = (float) (Math.cos(pitchTemp) * -Math.cos(yawTemp));
        return result;
    }

    public void toAngles(Vector3f direction) {

        float pitchTemp = 0;
        float yawTemp = 0;

        //Normalize the vector
        Vector3f dir = new Vector3f(direction);
        //dir.normalize();

        //Calculate the yaw and the pitch from the direction vector
        yawTemp = (float) Math.atan2(dir.x, dir.z);
        pitchTemp = (float) Math.atan2(dir.y, Math.sqrt(dir.x * dir.x + dir.z * dir.z));

        //Convert back into degrees
        pitchTemp = (float) ((pitchTemp * 180) / Math.PI);
        yawTemp = (float) ((yawTemp * 180) / Math.PI);

        //Clockwise
        this.pitch = -pitchTemp;
        this.yaw = -yawTemp;
    }

    public void add(float pitchAmount, float yawAmount, float rollAmount) {
        this.pitch += pitchAmount;
        this.yaw += yawAmount;
        this.roll += rollAmount;
        constrain();
    }

    public void constrain() {
        pitch %= 360;
        yaw %= 360;
    }

    public Vector3f getRotationRadian() {
        Vector3f res = new Vector3f();
        res.x = (float) Math.toRadians(pitch);
        res.y = (float) -Math.toRadians(yaw);
        res.z = (float) Math.toRadians(roll);
        return res;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public void setAngles(float pitch, float yaw) {
        setAngles(pitch, yaw, 0);
    }

    public void setAngles(float pitch, float yaw, float roll) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    public boolean equals(EulerAngle angle) {
        boolean a = pitch == angle.getPitch();
        boolean b = yaw == angle.getYaw();
        boolean c = roll == angle.getRoll();
        if (a && b && c)
            return true;
        return false;
    }

}
