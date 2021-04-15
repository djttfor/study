package com.ex.cache;

public interface VisitService {
    Long getVisitCount();
    void incrementVisitCount(String...ips);
}
