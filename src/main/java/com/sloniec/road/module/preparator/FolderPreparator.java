package com.sloniec.road.module.preparator;

import com.sloniec.road.framework.interf.IPreparator;
import com.sloniec.road.shared.commons.FileCommons;

public class FolderPreparator implements IPreparator {

    @Override
    public String prepare(String dataFolder) {
        FileCommons.checkIfIsFolder(dataFolder);
        System.out.println("Dane w folderze: " + dataFolder);
        return dataFolder;
    }
}
