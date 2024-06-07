package de.jensklingenberg.folders2kt

import java.io.File

fun File.listFolders(): List<File> {
    return this.listFiles()?.filter { it.isDirectory }?.sortedWith(FilesComparator) ?: emptyList()
}
