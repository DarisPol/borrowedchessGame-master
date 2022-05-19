package com.example.ajedrez

import android.util.Pair
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

object FirebaseController {

    private val UID_USERNAME_DB = "uid-username"
    private val MATCH_GAMES_DB = "game_match"
    private val GAMES_DB = "games"
    private val PLAYS_DB = "plays"

    private var mDatabase: DatabaseReference? = null
    private var matchGamesDb: DatabaseReference? = null
    private var gamesDb: DatabaseReference? = null
    private var playsDb: DatabaseReference? = null
    private var user: FirebaseUser? = null
    private var myTurn : Int = -1
    private var matchName : String = ""
    private var myTurnAux : Int = -1

    init {
        user = FirebaseAuth.getInstance().currentUser
        mDatabase = FirebaseDatabase.getInstance().reference.child(UID_USERNAME_DB)
        matchGamesDb = FirebaseDatabase.getInstance().reference.child(MATCH_GAMES_DB)
        gamesDb = FirebaseDatabase.getInstance().reference.child(GAMES_DB)
        playsDb = FirebaseDatabase.getInstance().reference.child(PLAYS_DB)
    }

    fun clearDB() { //Only for testing purposes, does not delete uid-username
        playsDb!!.removeValue()
        gamesDb!!.removeValue()
        matchGamesDb!!.removeValue()
    }

    fun getUsersOnline(activity: GameRoomActivity, userList: ArrayList<String>) {
        mDatabase!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userList.clear()
                for (userData in dataSnapshot.children) {
                    if (userData.key != user?.getUid()) {
                        userList.add(userData.key!!.toString())
                    }
                }
                activity.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    fun invitePlayer(activity: GameRoomActivity, username: String) {
        matchName = user!!.getUid() + "-" + username

        matchGamesDb!!.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val posibleMatchName = username + "-" + user!!.getUid()
                val fuiInvitado = dataSnapshot.child(posibleMatchName).value != null
                myTurn = if (fuiInvitado)
                    2
                else
                    1
                myTurnAux = myTurn
                if (fuiInvitado)
                    matchName = posibleMatchName
                val game = matchGamesDb!!.child(matchName)
                if (fuiInvitado) {
                    game.setValue(2L)
                    gamesDb!!.child(matchName).child("turn").addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.value != null) { //por testear en consola
                                if (dataSnapshot.value as Long? == 1L) {
                                    gamesDb!!.child(matchName).child("turn").removeEventListener(this)
                                    activity.iniciarPartida(false)
                                }
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {

                        }
                    })
                } else {
                    game.setValue(1L)
                    game.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.value != null) { //por testear en consola
                                if (dataSnapshot.value as Long? == 2L) {
                                    gamesDb!!.child(matchName).child("turn").removeEventListener(this)
                                    gamesDb!!.child(matchName).child("turn").setValue(myTurn)
                                    activity.iniciarPartida(true)
                                }
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {

                        }
                    })
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        println("Click")
    }

    fun turnListener(activity : OnlineGameActivity) {
        val gameDb = gamesDb!!.child(matchName)
        gameDb.child("turn").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.value as Long == myTurn.toLong()) {
                    activity.setItsMyTurn(true)
                    println("es mi turno")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    fun terminarTurno(activity : OnlineGameActivity) {
        gamesDb!!.child(matchName).child("turn").setValue(getTurnoOpuesto())
        activity.setItsMyTurn(false)
        println("ya no es mi turno")
    }

    private fun getTurnoOpuesto() : Int {
        if (myTurn == 1)
            return 2
        else
           return 1
    }

    private fun getTurnoOpuestoAux() : Int {
        if (myTurnAux == 1)
            myTurnAux = 2
        else
            myTurnAux = 1
        return myTurnAux
    }


    private fun leerMapAPair(map: Map<String, Object>) : Pair<Pair<Int, Int>, Pair<Int, Int>> {
        val first = map["first"] as Map<String, Object>
        val firstPairFirst = (first["first"] as Long).toInt()
        val firstPairSecond = (first["second"] as Long).toInt()
        val second = map["second"] as Map<String, Object>
        val secondPairFirst = (second["first"] as Long).toInt()
        val secondPairSecond = (second["second"] as Long).toInt()
        return Pair(Pair(firstPairFirst, firstPairSecond), Pair(secondPairFirst, secondPairSecond))
    }

//    fun leerMovimiento(activity: OnlineGameActivity) {
//        playsDb!!.child(matchName).child("turn").addValueEventListener(object : ValueEventListener {
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    val turnValue = (dataSnapshot.value as Long).toInt()
//                    val objectValue = mapValue.get("second") as Map<String, Object>
//                    val mov = leerMapAPair(objectValue)
//                    if (turnValue == getTurnoOpuesto()) {
//                        playsDb!!.child(matchName).removeEventListener(this)
//                        activity.leerMovimiento(mov)
//                    }
//            }
//
//            override fun onCancelled(p0: DatabaseError) {}
//        })
//    }

        fun leerMovimiento(activity: OnlineGameActivity) {
            gamesDb!!.child(matchName).child("turn").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val turnValue = (dataSnapshot.value as Long).toInt()
                    if (turnValue == myTurn) {
                        gamesDb!!.child(matchName).removeEventListener(this)
                        playsDb!!.child(matchName).child("mov").addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(p0: DataSnapshot) {
                                val mov = leerMapAPair(p0.value as Map<String, Object>)
                                activity.leerMovimiento(mov)
                            }

                            override fun onCancelled(p0: DatabaseError) {}
                        })

                    }
                }

                override fun onCancelled(p0: DatabaseError) {}
            })
    }


    fun realizarMovimiento(mov: Pair<Any, Any>) {
        val play = playsDb!!.child(matchName)
        play.child("mov").setValue(mov)
    }

}


