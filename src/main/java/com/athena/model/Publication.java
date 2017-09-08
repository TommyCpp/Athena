package com.athena.model;

import java.util.List;

/**
 * Created by Tommy on 2017/8/29.
 */
public interface Publication {
    List<? extends Copy> getCopies();

    String getTitle();

    Publisher getPublisher();
}
