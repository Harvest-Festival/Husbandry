package uk.joshiejack.husbandry.plugins;

import dev.latvian.kubejs.script.BindingsEvent;
import dev.latvian.kubejs.script.ScriptType;
import dev.latvian.kubejs.util.ClassFilter;
import uk.joshiejack.husbandry.Husbandry;
import uk.joshiejack.husbandry.plugins.kubejs.HusbandryUtils;

public class KubeJSPlugin extends dev.latvian.kubejs.KubeJSPlugin {
    @Override
    public void addClasses(ScriptType type, ClassFilter filter) {
        filter.allow("uk.joshiejack.husbandry.plugins.kubejs");
    }

    @Override
    public void addBindings(BindingsEvent event) {
        if (event.type != ScriptType.STARTUP)
            event.addClass(Husbandry.MODID, HusbandryUtils.class);
    }
}