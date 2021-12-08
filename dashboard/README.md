# 组件加载顺序
- 父组件：beforeCreate
- 父组件：created
- 父组件：beforeMount
- 子组件：beforeCreate
- 子组件：created
- 子组件：beforeMount
- 子组件：mounted
- 父组件：mounted

FormMaker组件在组件间移动不报错需要修改
vuedraggable组件中的三个文件，参考如下修改
https://github.com/SortableJS/vue.draggable.next/pull/52
vuedraggable.umd.js,vuedraggable.common.js,vuedraggable.js三个文件手工改动
insertNodeAt(this.$el, evt.item, evt.oldIndex);
改成：
insertNodeAt(evt.from, evt.item, evt.oldIndex);
