package org.com.yilian.oMClient.instructions;

public enum SpecifyCollection {
    UPDATE_AND_INSTALL("跟新并安装",1),
    VIEW_CONFIG("查看agent配置文件", 2),
    UPDATE_CONFIG("修改agent配置文件", 3),
    SHUTDOWN_AGENT("停止agent服务", 4),
    STARTUP_AGENT("开启agent服务", 5),
    SHUTDOWN_Expand("停止扩展服务", 6),
    STARTUP_Expand("开启扩展服务", 7);

    private String name;
    private Integer value;
    private SpecifyCollection(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
