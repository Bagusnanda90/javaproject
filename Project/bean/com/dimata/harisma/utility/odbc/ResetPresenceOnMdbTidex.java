/*
 * TransferPresenceFromMdb.java
 *
 * Created on December 18, 2004, 7:57 AM
 */
package com.dimata.harisma.utility.odbc;

import java.util.*;
import java.sql.*;

import com.dimata.qdep.db.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;

/**
 *
 * @author  gedhy
 */
public class ResetPresenceOnMdbTidex extends DBConnection {


    /**
     * Testing method
     */
    public static void main(String args[]) {
        TransferPresenceFromMdbTidex    objTransferPresenceFromMdb = new TransferPresenceFromMdbTidex();
        /*
        objTransferPresenceFromMdb.updateAllDataMdb("02","A","0");
        objTransferPresenceFromMdb.updateAllDataMdb("02","B","0");        
        objTransferPresenceFromMdb.updateAllDataMdb("02","C","0");        
        objTransferPresenceFromMdb.updateAllDataMdb("02","D","0");
        objTransferPresenceFromMdb.updateAllDataMdb("02","E","0");        
        objTransferPresenceFromMdb.updateAllDataMdb("02","F","0");        
        objTransferPresenceFromMdb.updateAllDataMdb("02","G","0");
        objTransferPresenceFromMdb.updateAllDataMdb("02","H","0");        
        objTransferPresenceFromMdb.updateAllDataMdb("02","I","0");        
         * */

        objTransferPresenceFromMdb.updateAllDataMdb("01","A","0");
        objTransferPresenceFromMdb.updateAllDataMdb("01","B","0");        
        objTransferPresenceFromMdb.updateAllDataMdb("01","C","0");        
        objTransferPresenceFromMdb.updateAllDataMdb("01","D","0");
        objTransferPresenceFromMdb.updateAllDataMdb("01","E","0");        
        objTransferPresenceFromMdb.updateAllDataMdb("01","F","0");        
        objTransferPresenceFromMdb.updateAllDataMdb("01","G","0");
        objTransferPresenceFromMdb.updateAllDataMdb("01","H","0");        
        objTransferPresenceFromMdb.updateAllDataMdb("01","I","0");        
    }    
}
