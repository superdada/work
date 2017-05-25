
package com.work.spark;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

// 面向对象 方式   乱了序的关系保存下来，还要把object放在一起 减少io 转内存
public class OneObjectData {

    private Map<String, String> headerData;

    public List<Map<String, String>> vDataMap = new ArrayList<Map<String, String>>();

    private List<List<String>> vData = new ArrayList<List<String>>();

    public Map<String, String> getHeaderData() {
        return headerData;
    }

    public void convertDataToMap(List<String> smrHeader) {
        if (this.vData.isEmpty()) {
            return;
        }
        for (List<String> oneLine : vData) {
            Map<String, String> dataMap = new HashMap<String, String>();
            for (int i = 0, size = oneLine.size(); i < size; i++) {
                dataMap.put(smrHeader.get(i), oneLine.get(i));
            }
            this.vDataMap.add(dataMap);
        }

    }

    public void setHeaderData(Map<String, String> headerData) {
        this.headerData = headerData;
    }

    public List<List<String>> getvData() {
        return vData;
    }

    public List<Map<String, String>> getvDataMap() {
        return vDataMap;
    }
}