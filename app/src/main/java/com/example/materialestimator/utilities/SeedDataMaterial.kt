package com.example.materialestimator.utilities

import com.example.materialestimator.storage.local.entities.MaterialCategory
import com.example.materialestimator.storage.local.entities.Material
import com.example.materialestimator.materials.*
import com.example.materialestimator.storage.local.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SeedDataMaterial {
    companion object {

        fun insert(instance: AppDatabase) {
            CoroutineScope(Dispatchers.IO).launch {
                categories().forEach {
                    instance.materialCategoryDao().insert(it)
                }
                drywall().forEach {
                    instance.materialDao().insert(it)
                }
                wood().forEach {
                    instance.materialDao().insert(it)
                }
                steel().forEach {
                    instance.materialDao().insert(it)
                }
            }
        }

        private fun categories(): ArrayList<MaterialCategory> {
            val list = ArrayList<MaterialCategory>()
            list.add(MaterialCategory(name = "Drywall"))
            list.add(MaterialCategory(name = "Wood"))
            list.add(MaterialCategory(name = "Steel"))
            return list
        }

        private fun drywall(): ArrayList<Material> {
            val list = ArrayList<Material>()
            list.add(
                Panel(
                    name = "Ultra Light Gypsum Panel",
                    unitprice = 64.00,
                    length = 8.0,
                    width = 4.0,
                    image = "ultralight_gypsum_panel",
                    materialCategoryId = 1
                )
            )
            list.add(
                Panel(
                    name = "Gypsum Ceiling Tile",
                    unitprice = 14.50,
                    length = 2.0,
                    width = 2.0,
                    image = "acoustical_ceiling_tile",
                    materialCategoryId = 1
                )
            )
            list.add(
                Furring(
                    name = "Furring Channel",
                    unitprice = 15.00,
                    length = 12.0,
                    image = "furring_channel",
                    materialCategoryId = 1
                )
            )
            list.add(
                WallAngle(
                    name = "Wall Angle",
                    unitprice = 8.0,
                    length = 10.0,
                    image = "gypsum_ceiling_wall_angle",
                    materialCategoryId = 1
                )
            )
            list.add(
                CChannel(
                    name = "C Channel",
                    unitprice = 50.0,
                    length = 16.0,
                    image = "gypsum_c_channel",
                    materialCategoryId = 1
                )
            )
            list.add(
                JointCompound(
                    name = "Joint Compound",
                    unitprice = 50.0,
                    coverage = 150.0,
                    image = "all_purpose_joint_compound",
                    materialCategoryId = 1
                )
            )
            list.add(
                Screw(
                    name = "Drywall Screw 1-1/4",
                    unitprice = 0.15,
                    coverage = 40.0,
                    image = "drywall_screw",
                    materialCategoryId = 1
                )
            )
            list.add(
                WallAngle(
                    name = "Acoustical Ceiling Wall Angle",
                    unitprice = 8.0,
                    length = 10.0,
                    image = "acoustical_wall_angle",
                    materialCategoryId = 1
                )
            )
            list.add(
                Tee(
                    name = "Main Tee",
                    unitprice = 25.0,
                    length = 12.0,
                    image = "cross_tee",
                    materialCategoryId = 1
                )
            )
            list.add(
                Tee(
                    name = "Cross Tee 4ft",
                    unitprice = 5.0,
                    length = 4.0,
                    image = "cross_tee",
                    materialCategoryId = 1
                )
            )
            list.add(
                Tee(
                    name = "Cross Tee 2ft",
                    unitprice = 3.50,
                    length = 2.0,
                    image = "cross_tee",
                    materialCategoryId = 1
                )
            )
            return list
        }

        private fun wood(): ArrayList<Material>{
            val list = ArrayList<Material>()
            list.add(
                Panel(
                    name = "1/2in Plywood",
                    unitprice = 150.00,
                    length = 8.0,
                    width = 4.0,
                    image = "plywood",
                    materialCategoryId = 2
                )
            )
            list.add(
                Lumber(
                    name = "2x4x10 RPP",
                    unitprice = 35.00,
                    length = 10.00,
                    image = "lumber_2x4",
                    materialCategoryId = 2
                )
            )
            list.add(
                Lumber(
                    name = "2x4x12 RPP",
                    unitprice = 75.00,
                    length = 12.00,
                    image = "lumber_4x4",
                    materialCategoryId = 2
                )
            )
            return list
        }

        private fun steel(): ArrayList<Material>{
            val list = ArrayList<Material>()
            list.add(
                Panel(
                    name = "1/16in Steel Sheet",
                    unitprice = 150.00,
                    length = 8.0,
                    width = 4.0,
                    image = "steel_corr_sheet",
                    materialCategoryId = 3
                )
            )
            list.add(
                Steel(
                    name = "1x1 RHS",
                    unitprice = 35.00,
                    length = 20.00,
                    image = "steel_rhs",
                    materialCategoryId = 3
                )
            )
            list.add(
                Steel(
                    name = "1/4in Angle",
                    unitprice = 75.00,
                    length = 20.00,
                    image = "steel_angle",
                    materialCategoryId = 3
                )
            )
            return list
        }


    }
}