package com.example.materialestimator.utilities

import com.example.materialestimator.models.Category
import com.example.materialestimator.models.Material
import com.example.materialestimator.models.Project
import com.example.materialestimator.models.materials.*
import com.example.materialestimator.storage.local.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataSource {
    companion object {

        fun populateDatabase(instance: AppDatabase) {
            CoroutineScope(Dispatchers.IO).launch {
                createProjectList().forEach {
                    instance.projectDao().insert(it)
                }
                createCategoryList().forEach {
                    instance.categoryDao().insert(it)
                }
                createDrywallMaterialList().forEach {
                    instance.materialDao().insert(it)
                }
                createWoodMaterialList().forEach {
                    instance.materialDao().insert(it)
                }
                createSteelMaterialList().forEach {
                    instance.materialDao().insert(it)
                }
            }
        }

        private fun createProjectList(): ArrayList<Project> {
            val list = ArrayList<Project>()
            list.add(Project(name = "108 Ridge Road Maraval", image = "project_1"))
            list.add(Project(name = "54 Lange Park Chaguanas", image = "project_2"))
            list.add(Project(name = "23 Main Road Tobago", image = "project_3"))
            list.add(Project(name = "Anglais Road Cumana", image = "project_4"))
            list.add(Project(name = "La Riviera Apt 1K", image = "project_5"))
            list.add(Project(name = "1/4 mm Toco Road Toco", image = "project_6"))
            return list
        }

        private fun createCategoryList(): ArrayList<Category> {
            val list = ArrayList<Category>()
            list.add(Category(name = "Drywall"))
            list.add(Category(name = "Wood"))
            list.add(Category(name = "Steel"))
            return list
        }

        private fun createDrywallMaterialList(): ArrayList<Material> {
            val list = ArrayList<Material>()
            list.add(
                Panel(
                    name = "Ultra Light Gypsum Panel",
                    unitprice = 64.00,
                    length = 8.0,
                    width = 4.0,
                    image = "ultralight_gypsum_panel",
                    categoryid = 1
                )
            )
            list.add(
                Panel(
                    name = "Gypsum Ceiling Tile",
                    unitprice = 14.50,
                    length = 2.0,
                    width = 2.0,
                    image = "acoustical_ceiling_tile",
                    categoryid = 1
                )
            )
            list.add(
                Furring(
                    name = "Furring Channel",
                    unitprice = 15.00,
                    length = 12.0,
                    image = "furring_channel",
                    categoryid = 1
                )
            )
            list.add(
                WallAngle(
                    name = "Wall Angle",
                    unitprice = 8.0,
                    length = 10.0,
                    image = "gypsum_ceiling_wall_angle",
                    categoryid = 1
                )
            )
            list.add(
                CChannel(
                    name = "C Channel",
                    unitprice = 50.0,
                    length = 16.0,
                    image = "gypsum_c_channel",
                    categoryid = 1
                )
            )
            list.add(
                JointCompound(
                    name = "Joint Compound",
                    unitprice = 50.0,
                    coverage = 150.0,
                    image = "all_purpose_joint_compound",
                    categoryid = 1
                )
            )
            list.add(
                Screw(
                    name = "Drywall Screw 1-1/4",
                    unitprice = 0.15,
                    coverage = 40.0,
                    image = "drywall_screw",
                    categoryid = 1
                )
            )
            list.add(
                WallAngle(
                    name = "Acoustical Ceiling Wall Angle",
                    unitprice = 8.0,
                    length = 10.0,
                    image = "acoustical_wall_angle",
                    categoryid = 1
                )
            )
            list.add(
                Tee(
                    name = "Main Tee",
                    unitprice = 25.0,
                    length = 12.0,
                    image = "cross_tee",
                    categoryid = 1
                )
            )
            list.add(
                Tee(
                    name = "Cross Tee 4ft",
                    unitprice = 5.0,
                    length = 4.0,
                    image = "cross_tee",
                    categoryid = 1
                )
            )
            list.add(
                Tee(
                    name = "Cross Tee 2ft",
                    unitprice = 3.50,
                    length = 2.0,
                    image = "cross_tee",
                    categoryid = 1
                )
            )
            return list
        }

        private fun createWoodMaterialList(): ArrayList<Material>{
            val list = ArrayList<Material>()
            list.add(
                Panel(
                    name = "1/2in Plywood",
                    unitprice = 150.00,
                    length = 8.0,
                    width = 4.0,
                    image = "plywood",
                    categoryid = 2
                )
            )
            list.add(
                Lumber(
                    name = "2x4x10 RPP",
                    unitprice = 35.00,
                    length = 10.00,
                    image = "lumber_2x4",
                    categoryid = 2
                )
            )
            list.add(
                Lumber(
                    name = "2x4x12 RPP",
                    unitprice = 75.00,
                    length = 12.00,
                    image = "lumber_4x4",
                    categoryid = 2
                )
            )
            return list
        }

        private fun createSteelMaterialList(): ArrayList<Material>{
            val list = ArrayList<Material>()
            list.add(
                Panel(
                    name = "1/16in Steel Sheet",
                    unitprice = 150.00,
                    length = 8.0,
                    width = 4.0,
                    image = "steel_corr_sheet",
                    categoryid = 3
                )
            )
            list.add(
                Steel(
                    name = "1x1 RHS",
                    unitprice = 35.00,
                    length = 20.00,
                    image = "steel_rhs",
                    categoryid = 3
                )
            )
            list.add(
                Steel(
                    name = "1/4in Angle",
                    unitprice = 75.00,
                    length = 20.00,
                    image = "steel_angle",
                    categoryid = 3
                )
            )
            return list
        }


    }
}