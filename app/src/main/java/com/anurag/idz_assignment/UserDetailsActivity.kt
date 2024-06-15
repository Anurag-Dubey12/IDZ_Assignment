package com.anurag.idz_assignment

import android.database.Cursor
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var tvTitle: TextView
    private lateinit var tvSubtitle: TextView
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_details)
        databaseHelper = DatabaseHelper(this)

        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        tvTitle = TextView(this).apply {
            text = "Registration Successful"
            textSize = 24f
            gravity = Gravity.CENTER
            setTextColor(ContextCompat.getColor(this@UserDetailsActivity, android.R.color.black))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._20sdp)
            }
        }
        tvSubtitle = TextView(this).apply {
            text = "Your Details are Submitted successfully"
            textSize = 16f
            gravity = Gravity.CENTER
            setTextColor(ContextCompat.getColor(this@UserDetailsActivity, android.R.color.black))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val tableLayout = TableLayout(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        val headerRow = TableRow(this).apply {
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
        }
        val headerName = TextView(this).apply {
            text = "Name"
            textSize = 16f
            gravity = Gravity.CENTER
            setTextColor(ContextCompat.getColor(this@UserDetailsActivity, android.R.color.black))
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
            ).apply {
                rightMargin = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._80sdp)
                leftMargin = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._30sdp)
            }
        }

        val headerDob = TextView(this).apply {
            text = "Birthday"
            textSize = 16f
            gravity = Gravity.CENTER
            setTextColor(ContextCompat.getColor(this@UserDetailsActivity, android.R.color.black))
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
            ).apply {
                rightMargin = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._50sdp)
            }
        }

        val headerEmail = TextView(this).apply {
            text = "Email Id"
            textSize = 16f
            gravity = Gravity.END
            setTextColor(ContextCompat.getColor(this@UserDetailsActivity, android.R.color.black))
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
        }

        headerRow.addView(headerName)
        headerRow.addView(headerDob)
        headerRow.addView(headerEmail)
        tableLayout.addView(headerRow)

        rootLayout.addView(tvTitle)
        rootLayout.addView(tvSubtitle)
        rootLayout.addView(tableLayout)

        setContentView(rootLayout)
        displayUserData(tableLayout)
    }

    private fun displayUserData(tableLayout: TableLayout) {
        val cursor: Cursor = databaseHelper.getUserData()
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val dob = cursor.getString(cursor.getColumnIndexOrThrow("dob"))
                val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))

                val dataRow = TableRow(this).apply {
                    layoutParams = TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                    )
                }

                val nameText = TextView(this).apply {
                    text = name
                    textSize = 16f
                    gravity = Gravity.CENTER
                    setTextColor(ContextCompat.getColor(this@UserDetailsActivity, android.R.color.black))
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                    ).apply {
                        rightMargin = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._5sdp)
                    }
                }

                val dobText = TextView(this).apply {
                    text = dob
                    textSize = 16f
                    gravity = Gravity.CENTER
                    setTextColor(ContextCompat.getColor(this@UserDetailsActivity, android.R.color.black))
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                    ).apply {
//                        rightMargin = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp)
                    }
                }

                val emailText = TextView(this).apply {
                    text = email
                    textSize = 16f
                    gravity = Gravity.CENTER
                    setTextColor(ContextCompat.getColor(this@UserDetailsActivity, android.R.color.black))
                    layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                }

                dataRow.addView(nameText)
                dataRow.addView(dobText)
                dataRow.addView(emailText)

                tableLayout.addView(dataRow)

                val space = Space(this)
                space.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._5sdp)
                )
                tableLayout.addView(space)

            } while (cursor.moveToNext())
        }
        cursor.close()
        val headerSpace = Space(this)
        headerSpace.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp)
        )
        tableLayout.addView(headerSpace, 1)
    }




}