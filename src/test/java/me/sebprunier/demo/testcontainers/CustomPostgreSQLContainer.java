package me.sebprunier.demo.testcontainers;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.utility.DockerImageName;

public class CustomPostgreSQLContainer<SELF extends CustomPostgreSQLContainer<SELF>> extends PostgreSQLContainer<SELF> {

    private String[] initScripts;

    public CustomPostgreSQLContainer(String dockerImageName) {
        super(dockerImageName);
    }

    public CustomPostgreSQLContainer(DockerImageName dockerImageName) {
        super(dockerImageName);
    }

    public SELF withInitScripts(String... initScripts) {
        this.initScripts = initScripts;
        return this.self();
    }

    protected void runInitScriptIfRequired() {
        super.runInitScriptIfRequired();
        if (this.initScripts != null) {
            for (String script : initScripts) {
                ScriptUtils.runInitScript(this.getDatabaseDelegate(), script);
            }
        }
    }

}
