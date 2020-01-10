package com.neusiri.husky.core.mail.client.config;

import com.xxl.rpc.registry.ServiceRegistry;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class HuskyLocalServiceRegistry extends ServiceRegistry {

    private TreeSet<String> addresses;

    @Override
    public void start(Map<String, String> param) {
        String serverAddress = param.get("serverAddress");
        addresses = new TreeSet<>();
        addresses.add(serverAddress);
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean registry(Set<String> keys, String value) {
        return false;
    }

    @Override
    public boolean remove(Set<String> keys, String value) {
        return false;
    }

    @Override
    public Map<String, TreeSet<String>> discovery(Set<String> keys) {

        Map<String, TreeSet<String>> treeSetMap = new TreeMap<>();
        for (String key : keys) {
            treeSetMap.put(key, addresses);
        }
        return treeSetMap;
    }

    @Override
    public TreeSet<String> discovery(String key) {
        return addresses;
    }
}
