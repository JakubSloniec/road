package com.sloniec.road.module.preparator;

import com.sloniec.road.framework.interf.IPreparator;
import com.sloniec.road.shared.commons.FileCommons;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FolderPreparator implements IPreparator {

    @Override
    public String prepare(String dataFolder) {
        FileCommons.checkIfIsFolder(dataFolder);
        log.info("Dane w folderze: [{}]", dataFolder);
        return dataFolder;
    }
}
