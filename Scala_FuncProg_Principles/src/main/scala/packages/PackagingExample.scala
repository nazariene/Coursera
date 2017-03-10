package packages

class PackagingExample {   //In packages

    val name: String = "123"


}

package innerpackage {
    class InnerPackageExample {     //in packages.innerpackage - i.e. packages.innerpackage.InnerPackageExample

    }

    package innerinnerpackage {
        class InnerInnerPackageExample {

        }
    }
}
