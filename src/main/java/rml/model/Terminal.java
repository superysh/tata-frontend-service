package rml.model;

/**
 * Created by edward-echo on 2016/3/25.
 */
public class Terminal {
    private String uuid;
    private String version;
    private int forceUpdateIncrement;
    private String ldesc;
    private int type;
    private String url;
    private int increment;
    private boolean needUpdate;

    private String secretKey;

    private String randomKey;


    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }


    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getForceUpdateIncrement() {
        return forceUpdateIncrement;
    }

    public void setForceUpdateIncrement(int forceUpdateIncrement) {
        this.forceUpdateIncrement = forceUpdateIncrement;
    }

    public String getLdesc() {
        return ldesc;
    }

    public void setLdesc(String ldesc) {
        this.ldesc = ldesc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIncrement() {
        return increment;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }
}
