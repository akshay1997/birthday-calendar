/*
 * Copyright (C) 2013 Dominik Schürmann <dominik@dominikschuermann.de>
 *
 * This file is part of Birthday Adapter.
 *
 * Birthday Adapter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Birthday Adapter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Birthday Adapter.  If not, see <http://www.gnu.com.example.snigdha.birthdaycalendarapp.org/licenses/>.
 *
 */

package com.example.akshay.birthdayapp;

import java.util.HashSet;
import java.util.Iterator;

import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ProviderHelper {

    public static void setAccountBlacklist(Context context, HashSet<Account> blacklist) {
        // clear table
        context.getContentResolver().delete(BirthdayAdapterContract.AccountBlacklist.CONTENT_URI, null, null);

        ContentValues[] values = new ContentValues[blacklist.size()];

        // build values array based on HashSet
        Iterator<Account> itr = blacklist.iterator();
        int i = 0;
        while (itr.hasNext()) {
            Account acc = itr.next();
            values[i] = new ContentValues();
            values[i].put(BirthdayAdapterContract.AccountBlacklist.ACCOUNT_NAME, acc.name);
            values[i].put(BirthdayAdapterContract.AccountBlacklist.ACCOUNT_TYPE, acc.type);

            i++;
        }

        // insert as bulk operation
        context.getContentResolver().bulkInsert(BirthdayAdapterContract.AccountBlacklist.CONTENT_URI, values);
    }

    public static HashSet<Account> getAccountBlacklist(Context context) {
        HashSet<Account> hashSet = new HashSet<Account>();
        Cursor cursor = getAccountBlacklistCursor(context, null, null);

        try {
            while (cursor != null && cursor.moveToNext()) {
                Account acc = new Account(
                        cursor.getString(cursor.getColumnIndexOrThrow(BirthdayAdapterContract.AccountBlacklist.ACCOUNT_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(BirthdayAdapterContract.AccountBlacklist.ACCOUNT_TYPE)));

                hashSet.add(acc);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }

        return hashSet;
    }

    private static Cursor getAccountBlacklistCursor(Context context, String selection, String[] selectionArgs) {
        return context.getContentResolver().query(
                BirthdayAdapterContract.AccountBlacklist.CONTENT_URI,
                new String[]{BirthdayAdapterContract.AccountBlacklist._ID,
                        BirthdayAdapterContract.AccountBlacklist.ACCOUNT_NAME,
                        BirthdayAdapterContract.AccountBlacklist.ACCOUNT_TYPE},
                selection,
                selectionArgs,
                BirthdayAdapterContract.AccountBlacklist.DEFAULT_SORT);
    }

}
