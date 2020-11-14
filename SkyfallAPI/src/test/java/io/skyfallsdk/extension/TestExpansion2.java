package io.skyfallsdk.extension;

import io.skyfallsdk.expansion.Expansion;
import io.skyfallsdk.expansion.ExpansionInfo;

@ExpansionInfo(name = "test2", version = "${project.version}", authors = { "Obadiah Crowe" }, dependencies = { "test" })
public class TestExpansion2 implements Expansion {

    @Override
    public void onStartup() {

    }

    @Override
    public void onShutdown() {

    }
}
