package app.revanced.patches.oncf.misc.pairip

import app.revanced.patcher.patch.resourcePatch
import org.w3c.dom.Element

@Suppress("unused")
val removePairipComponentsPatch = resourcePatch(
    name = "Remove PairIP components",
    description = "Removes PairIP license check components from AndroidManifest.xml.",
) {
    compatibleWith("ma.oncf.oncfmobileapp")

    execute {
        document("AndroidManifest.xml").use { document ->
            val applicationNode = document.getElementsByTagName("application").item(0)
            val childNodes = applicationNode.childNodes
            val nodesToRemove = mutableListOf<Element>()

            // Find all PairIP-related components to remove
            for (i in 0 until childNodes.length) {
                val node = childNodes.item(i)
                if (node is Element) {
                    val nodeName = node.getAttribute("android:name")
                    
                    // Check if this is a PairIP component
                    if (nodeName == "com.pairip.licensecheck.LicenseActivity" ||
                        nodeName == "com.pairip.licensecheck.LicenseContentProvider" ||
                        nodeName == "com.pairip.licensecheck.LicenseContentProvider1") {
                        nodesToRemove.add(node)
                    }
                }
            }

            // Remove the identified nodes
            nodesToRemove.forEach { node ->
                applicationNode.removeChild(node)
            }
        }
    }
}
