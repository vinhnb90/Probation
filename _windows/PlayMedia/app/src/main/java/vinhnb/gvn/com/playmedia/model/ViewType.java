package vinhnb.gvn.com.playmedia.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static vinhnb.gvn.com.playmedia.model.ViewType.TYPE_VIEW;

@Retention(RetentionPolicy.RUNTIME)
@IntDef({TYPE_VIEW})
public @interface ViewType {
    int TYPE_VIEW = 1;
}
