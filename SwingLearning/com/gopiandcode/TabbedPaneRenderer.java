package com.gopiandcode;

import java.awt.*;

public interface TabbedPaneRenderer<T> {
    Container renderModel(T model);
}
