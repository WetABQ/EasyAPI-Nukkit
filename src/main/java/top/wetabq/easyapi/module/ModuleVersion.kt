package top.wetabq.easyapi.module


class ModuleVersion(val v1: Int, val v2: Int, val v3: Int) {
    override fun toString(): String {
        return "v$v1.$v2.${v3}"
    }
}