package org.example;

public class Util {
    public static boolean isMessageInOrder(Long prevId, Long currentId) {
        if (prevId > 1 && currentId == 0) {
            return true;
        }
        return prevId < currentId;
    }
}
