// **************************************************************************
// Copyright 2007 - 2008 The JSLab Team, Tavs Dokkedahl and Allan Jacobs
// Contact: http://www.jslab.dk/contact.php
//
// This file is part of the JSLab Standard Library (JSL) Program.
//
// JSL is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 3 of the License, or
// any later version.
//
// JSL is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.
// ***************************************************************************
// File created 2009-01-23 17:22:36

// Decoding function. Default is decodeURIComponent
Cookie.decode = decodeURIComponent;

// Copy an array
Array.prototype.copy =
  function() {
    return [].concat(this);
  };

// Return elements which are in A but not in arg0 through argn
Array.prototype.diff =
  function() {
    var a1 = this;
    var a = a2 = null;
    var n = 0;
    while(n < arguments.length) {
      a = [];
      a2 = arguments[n];
      var l = a1.length;
      var l2 = a2.length;
      var diff = true;
      for(var i=0; i<l; i++) {
        for(var j=0; j<l2; j++) {
          if (a1[i] === a2[j]) {
            diff = false;
            break;
          }
        }
        diff ? a.push(a1[i]) : diff = true;
      }
      a1 = a;
      n++;
    }
    return a.unique();
  };

// Check whether n number of arrays are disjoint
Array.prototype.disjoint =
  function() {
    var args = [];
    var l = arguments.length;
    if (!l)
      return true;
    for(var i=0; i<l; i++)
      args.push(arguments[i]);
    return Array.prototype.intersect.apply(this,args).length > 0 ? false : true;
  };

// Compute the intersection of n arrays
Array.prototype.intersect =
  function() {
    if (!arguments.length)
      return [];
    var a1 = this;
    var a = a2 = null;
    var n = 0;
    while(n < arguments.length) {
      a = [];
      a2 = arguments[n];
      var l = a1.length;
      var l2 = a2.length;
      for(var i=0; i<l; i++) {
        for(var j=0; j<l2; j++) {
          if (a1[i] === a2[j])
            a.push(a1[i]);
        }
      }
      a1 = a;
      n++;
    }
    return a.unique();
  };

// Apply each member of the array as the first argument to the function f
Array.prototype.mapFunction =
  function(f) {
    var l = this.length;
    for(var i=0; i<l; i++)
      f(this[i]);
  };

// Apply the method m to each member of an array
Array.prototype.mapMethod =
  function(m) {
    var a = [];
    for(var i=1; i<arguments.length; i++)
      a.push(arguments[i]);
    var l = this.length;
    for(var i=0; i<l; i++)
      m.apply(this[i],a);
  };

// Pad an array to given size with a given value
Array.prototype.pad =
  function(s,v) {
    var l = Math.abs(s) - this.length;
    var a = [].concat(this);
    if (l <= 0)
      return a;
    for(var i=0; i<l; i++)
      s < 0 ? a.unshift(v) : a.push(v);
    return a;
  };

// Randomize elements in an array
Array.prototype.randomize =
  function(ru) {
    if (!ru)
      this.sort(function(){return ((Math.random() * 3) | 0) - 1;});
    else {
      var a = [].concat(this);
      var l = this.length;
      var al = n = 0;
      for(var i=0; i<l; i++) {
        al = a.length;
        // Get random integer in [0,a.length-1]
        n = Math.floor((Math.random() * al));
        // Copy random element from a to this
        this[i] = a[n];
        // If n was last element in a just pop a[n]
        if (n == al - 1)
          a.pop();
        // Else copy last element from a to n and pop last element from a
        else {
          a[n] = a[al - 1];
          a.pop();
        }
      }
    }
  };

// Remove values from an array optionally using a custom function
Array.prototype.remove =
  function(f) {
    if (!f)
      f = function(i) {return i == undefined || i == null ? true : false;};
    var l = this.length;
    var n = 0;
    for(var i=0; i<l; i++)
      f(this[i]) ? n++ : this[i-n] = this[i];
    this.length = this.length - n;
  };

// Get the union of n arrays
Array.prototype.union =
  function() {
    var a = [].concat(this);
    var l = arguments.length;
    for(var i=0; i<l; i++) {
      a = a.concat(arguments[i]);
    }
    return a.unique();
  };

// Return new array with duplicate values removed
Array.prototype.unique =
  function() {
    var a = [];
    var l = this.length;
    for(var i=0; i<l; i++) {
      for(var j=i+1; j<l; j++) {
        // If this[i] is found later in the array
        if (this[i] === this[j])
          j = ++i;
      }
      a.push(this[i]);
    }
    return a;
  };

// Default name/value seperator is ':'
Cookie.valueSeparator = ':';

// Cookie object constructor
function Cookie(name, age, path, domain, secure) {
  // Friendly name of cookie
  this.$name = name;
  // Lifetime in seconds. Default value is 0
  this.$age = age ? age : 0;
  // Creation date
  this.$date = (new Date()).getTime();
  // Default path is path from where document originated
  this.$path = path ? path : window.location.pathname.replace(/(\/)[^\/]*$/,'$1');
  // Default domain is domain from where document originated
  this.$domain = domain ? domain : window.location.hostname;
  // Default is insecure (HTTP)
  this.$secure = secure ? secure : false;
}

// Encoding function. Default is encodeURIComponent
Cookie.encode = encodeURIComponent;

// Test whether cookies are enabled
Cookie.isEnabled =
  function() {
    var c = new Cookie('test');
    c.write();
    try {
      Cookie.read('test');
    }
    catch(e) {
      return false;
    }
    c.remove();
    return true;
  };

// Get stored cookie
Cookie.read =
  function(name) {
    // Start position of cookie
    var pos = document.cookie.indexOf(name+'=');
    if (pos != -1) {
      var c = new Cookie(name);
      // Start position of named cookie
      var s = pos + name.length + 1;
      // End position of named cookie
      var e = document.cookie.indexOf(';',s);
      if (e != -1)
        var p = document.cookie.substring(s,e);
      else
        var p = document.cookie.substring(s);
      // Decode and split into array
      var a = Cookie.decode(p).split(Cookie.pairSeparator);
      // Temp. variable
      var t = null;
      for(var i=0; i<a.length; i++) {
        t = a[i].split(Cookie.valueSeparator);
        c[t[0]] = t[1];
      }
      return c;
    }
    else
      throw new Error('Cookie: No cookie with name \'' + name + '\' found.');
  };

// Delete cookie
Cookie.prototype.remove =
  function() {
    // Setting the age to -1 * now will create an expiration
    // date of 1/1 1970 when the cookie is written
    this.$age = (new Date()).getTime() / -1000;
    this.write();
  };

// Custom toString method
Cookie.prototype.toString =
  function() {
    var d = new Date(this.$date * 1);
    var e = new Date(this.$date * 1 + this.$age * 1000);
    var s = 'Name: ' + this.$name + '\nCreated: ' + d + '\nMax-age: ' + this.$age +  '\nExpires on: ' + e + '\nPath: ' + this.$path + '\nDomain: ' + this.$domain + '\nSecure: ' + this.$secure + '\n\n';
    for(var p in this) {
      if (this[p].constructor != Function && p.charAt(0) != '$') 
        s += p + ': ' + this[p] + '\n';
    }
    return s;
  };

// Write a named cookie
Cookie.prototype.write =
  function() {
    var s = '';
    // Don't save  methods
    for(var p in this)
      s += this[p].constructor != Function ? p + Cookie.valueSeparator + this[p] + Cookie.pairSeparator : '';
    // Delete trailing pairSeparator and encode string
    s = Cookie.encode(s.substring(0, s.length-1));
    // expire date for IE
    var d = (new Date()).getTime() + this.$age * 1000;
    d = (new Date(d)).toUTCString();
    // Save cookie
    document.cookie = this.$name + '=' + s + '; expires=' + d + '; max-age=' + this.$age + '; path=' + this.$path + '; domain=' + this.$domain + '; ' + this.$secure + ';';
  };

// Name of the months
Date.nameOfMonths = ['January','February','March','April','May','June','July','August','September','October','November','December'];

// Return date of monday in any given week and year
Date.getFirstDateInWeek =
  function (y,w) {
    // 4th of january is always in week 1 of any year
    var d = (new Date(y, 0, 4)).getISODay() - 1;
    return new Date(y, 0, 4 - d + (w - 1) * 7);
  };

// Format a date according to a specified format
Date.prototype.format =
  function(s,utc) {
    // Split into array
    s = s.split('');
    var l = s.length;
    var r = '';
    var n = m = null;
    for (var i=0; i<l; i++) {
      switch(s[i]) {
        // Day of the month, 2 digits with leading zeros: 01 to 31
        case 'd':
          n = utc ? this.getUTCDate() : this.getDate();
          if (n * 1 < 10)
            r += '0';
          r += n;
          break;
        // A textual representation of a day, three letters:  Mon through Sun 
        case 'D':
          r += this.getNameOfDay(utc).substring(0,3);
          break;
        // Day of the month without leading zeros:   1 to 31
        case 'j':
          r += utc ? this.getUTCDate() : this.getDate();
          break;
        // Lowercase l A full textual representation of the day of the week: Sunday (0) through Saturday (6) 
        case 'l':
          r += this.getNameOfDay(utc);
          break;
        // ISO-8601 numeric representation of the day of the week: 1 (for Monday) through 7 (for Sunday) 
        case 'N':
          r += this.getISODay(utc);
          break;
        // English ordinal suffix for the day of the month, 2 characters
        case 'S':
          r += this.getDaySuffix(utc);
          break;
        // Numeric representation of the day of the week: 0 (for Sunday) through 6 (for Saturday) 
        case 'w':
          r += utc ? this.getUTCDay() : this.getDay();
          break;
        // The day of the year (starting from 0) 0 through 365
        case 'z':
          n = 0;
          m = utc ? this.getUTCMonth() : this.getMonth();
          for(var i=0; i<m; i++)
            n += Date.daysInMonth[i]
          if (this.isLeapYear())
            n++;
          n += utc ? this.getUTCDate() : this.getDate();
          n--;
          r += n;
          break;
        //   break;
        // ISO-8601 week number of year, weeks starting on Monday
        case 'W':
          r += this.getISOWeek(utc);
          break;
        // A full textual representation of a month, such as January or March:  January through December 
        case 'F':
          r += this.getNameOfMonth(utc);
          break;
        // Numeric representation of a month, with leading zeros 01 through 12 
        case 'm':
          n = utc ? this.getUTCMonth() : this.getMonth();
          n++;
          if (n < 10)
            r += '0';
          r += n;
          break;
        // A short textual representation of a month, three letters:  Jan through Dec 
        case 'M':
          r += this.getNameOfMonth(utc).substring(0,3);
          break;
        // Numeric representation of a month, without leading zeros:  1 through 12 
        case 'n':
          n = utc ? this.getUTCMonth() : this.getMonth();
           r += ++n;
          break;
        // Number of days in the given month: 28 through 31 
        case 't':
          r += this.getDaysInMonth(utc);
          break;
        // Whether it's a leap year:  1 if it is a leap year, 0 otherwise.
        case 'L':
          if (this.isLeapYear(utc))
            r += '1';
          else
            r += '0';
          break;
        // ISO-8601 year number. This has the same value as Y, except that if the ISO week number (W) belongs to the previous or next year, that year is used instead
        /*
        case 'o':
          break;
        */
        // A full numeric representation of a year, 4 digits
        case 'Y':
          r += utc ? this.getUTCFullYear() : this.getFullYear();
          break;
        // A two digit representation of a year
        case 'y':
          n = utc ? this.getUTCFullYear() : this.getFullYear();
          r += (n + '').substring(2);
          break;
        // Lowercase Ante meridiem and Post meridiem am or pm 
        case 'a':
          n = utc ? this.getUTCHours() : this.getHours();
          r += n < 12 ? 'am' : 'pm';
          break;
        // Uppercase Ante meridiem and Post meridiem AM or PM 
        case 'A':
          n = utc ? this.getUTCHours() : this.getHours();
          r += n < 12 ? 'AM' : 'PM';
          break;
        // Swatch Internet time 000 through 999 
        // case 'B':
        //   break;
        // 12-hour format of an hour without leading zeros
        case 'g':
          n = utc ? this.getUTCHours() : this.getHours();
          if (n > 12)
            n -= 12;
          r += n;
          break;
        // 24-hour format of an hour without leading zeros 0 through 23
        case 'G':
          r += this.getHours();
          break;
        //  12-hour format of an hour with leading zeros 01 through 12 
        case 'h':
          n = utc ? this.getUTCHours() : this.getHours();
          if (n > 12)
            n -= 12;
          if (n < 10)
            r += '0';
          r += n;
          break;
        // 24-hour format of an hour with leading zeros 00 through 23 
        case 'H':
          n = utc ? this.getUTCHours() : this.getHours();
          if (n < 10)
            r += '0';
          r += n;
          break;
        // i Minutes with leading zeros 00 to 59 
        case 'i':
          n = utc ? this.getUTCMinutes() : this.getMinutes();
          if (n < 10)
            r += '0';
          r += n;
          break;
        // s Seconds, with leading zeros 00 through 59 
        case 's':
          n = utc ? this.getUTCSeconds() : this.getSeconds();
          if (n < 10)
            r += '0';
          r += n;
          break;
        // Milliseconds
        case 'u':
          r += utc ? this.getUTCMilliseconds() : this.getMilliseconds();
          break;
        // Timezone identifier
        // case 'e':
        //   break;
        // Whether or not the date is in daylight saving time 1 if Daylight Saving Time, 0 otherwise. 
        case 'I':
          if (this.getMinutes() != this.getUTCMinutes)
            r += '1';
          else
            r += '0';
          break;
        // Difference to Greenwich time (GMT) in hours
        case 'O':
          n = this.getTimezoneOffset() / 60;
          if (n >= 0)
            r += '+';
          else
            r += '-';
          n = Math.abs(n);
          if (Math.abs(n) < 10)
            r += '0';
           r += n + '00';
          break;
        // Difference to Greenwich time (GMT) with colon between hours and minutes: Example: +02:00 
        case 'P':
          n = this.getTimezoneOffset() / 60;
          if (n >= 0)
            r += '+';
          else
            r += '-';
          n = Math.abs(n);
          if (Math.abs(n) < 10)
            r += '0';
           r += n + ':00';
          break;
        // T Timezone abbreviation EST, MDT etc. 
        // case 'T':
        //   break;
        // Z Timezone offset in seconds. The offset for timezones west of UTC is always negative, and for those east of UTC is always positive. 
        case 'Z':
          r += this.getTimezoneOffset() * 60;
          break;
        // ISO 8601 date: 2004-02-12T15:19:21+00:00 
        case 'c':
          r += this.format('Y-m-d',utc) + 'T' + this.format('H:i:sP',utc);
          break;
        // RFC 2822 formatted date Example: Thu, 21 Dec 2000 16:01:07 +0200 
        case 'r':
          r += this.format('D, j M Y H:i:s P',utc);
          break;
        case 'U':
          r += this.getTime();
          break;
        default:
          r += s[i];
      }
    }
    return r
  };

// Gen the english suffix for dates
Date.prototype.getDaySuffix =
  function(utc) {
    var n = utc ? this.getUTCDate() : this.getDate();
    // If not the 11th and date ends at 1
    if (n != 11 && (n + '').match(/1$/))
      return 'st';
    // If not the 12th and date ends at 2
    else if (n != 12 && (n + '').match(/2$/))
      return 'nd';
    // If not the 13th and date ends at 3
    else if (n != 13 && (n + '').match(/3$/))
      return 'rd';
    else
      return 'th';
  };

// Return the number of days in the month
Date.prototype.getDaysInMonth =
  function(utc) {
    var m = utc ? this.getUTCMonth() : this.getMonth();
    // If feb.
    if (m == 1)
      return this.isLeapYear() ? 29 : 28;
    // If Apr, Jun, Sep or Nov return 30; otherwise 31
    return (m == 3 || m == 5 || m == 8 || m == 10) ? 30 : 31;
  };

// Return the ISO day number for a date
Date.prototype.getISODay =
  function(utc) {
    // Native JS method - Sunday is 0, monday is 1 etc.
    var d = utc ? this.getUTCDay() : this.getDay();
    // Return d if not sunday; otherwise return 7
    return d ? d : 7;
  };

// Get ISO week number of the year
// The algorithm is credit to Claus Tøndering and is taken from his calendar FAQ
// See http://www.tondering.dk/claus/cal/node8.html#SECTION00880000000000000000
// for more information
// Integer division: a/b|0
Date.prototype.getISOWeek =
  function(utc) {
    var y = utc ? this.getUTCFullYear(): this.getFullYear();
    var m = utc ? this.getUTCMonth() + 1: this.getMonth() + 1;
    var d = utc ? this.getUTCDate() : this.getDate();
    // If month jan. or feb.
    if (m < 3) {
      var a = y - 1;
      var b = (a / 4 | 0) - (a / 100 | 0) + (a / 400 | 0);
      var c = ( (a - 1) / 4 | 0) - ( (a - 1) / 100 | 0) + ( (a - 1) / 400 | 0);
      var s = b - c;
      var e = 0;
      var f = d - 1 + 31 * (m - 1);
    }
    // If month mar. through dec.
    else {
      var a = y;
      var b = (a / 4 | 0) - ( a / 100 | 0) + (a / 400 | 0);
      var c = ( (a - 1) / 4 | 0) - ( (a - 1) / 100 | 0) + ( (a - 1) / 400 | 0);
      var s = b - c;
      var e = s + 1;
      var f = d + ( (153 * (m - 3) + 2) / 5 | 0) + 58 + s;
    }
    var g = (a + b) % 7;
    // ISO Weekday (0 is monday, 1 is tuesday etc.)
    var d = (f + g - e) % 7;
    var n = f + 3 - d;
    if (n < 0)
      var w = 53 - ( (g - s) / 5 | 0);
    else if (n > 364 + s)
      var w = 1;
    else
      var w = (n / 7 | 0) + 1;
    return w;
  };

// Return the name of the weekday
Date.prototype.getNameOfDay =
  function(utc) {
    var d = this.getISODay(utc) - 1;
    return Date.nameOfDays[d];
  };

// Return the name of the month
Date.prototype.getNameOfMonth =
  function(utc) {
    var m = utc ? this.getUTCMonth() : this.getMonth();
    return Date.nameOfMonths[m];
  };

// Rewrite native Date.getTimezoneOffset to return values with correct sign
Date.prototype._getTimezoneOffset = Date.prototype.getTimezoneOffset;
Date.prototype.getTimezoneOffset =
  function() {
    return this._getTimezoneOffset() * -1;
  };

// Retuns true if year is a leap year; otherwise false
Date.prototype.isLeapYear =
  function(utc) {
    var y = utc ? this.getUTCFullYear() : this.getFullYear();
    return !(y % 4) && (y % 100) || !(y % 400) ? true : false;
  };

// Set the week number of a date. The date becomes the monday of that week
Date.prototype.setISOWeek =
  function(w,utc) {
    var y = utc ? this.getUTCFullYear() : this.getFullYear();
    var d = Date.getFirstDateInWeek(y,w);
    this.setTime(d.getTime());
  };

Function.prototype.defer =
  function(n,o) {
    // Get arguments as array
    var a = [];
    for(var i=2; i<arguments.length; i++)
      a.push(arguments[i]);
    var that = this;
    window.setTimeout(function(){return that.apply(o,a);},n);
  };

// Return the names of the arguments as strings in a an array
Function.prototype.getArguments =
  function() {
    // Get content between first ( and last )
    var m = this.toString().match(/\((.*)\)/)[1];
    // Strip spaces
    m = m.replace(/\s*/g,'');
    // String to array
    return m.split(',');
  };

// Return the body of a function
Function.prototype.getBody =
  function() {
    // Get content between first { and last }
    var m = this.toString().match(/\{([\s\S]*)\}/m)[1];
    // Strip comments
    return m.replace(/^\s*\/\/.*$/mg,'');
  };

// Return the name of a function
Function.prototype.getName =
  function() {
    var m = this.toString().match(/^function\s(\w+)/);
    return m ? m[1] : "anonymous";
  };

// Time a function n times and return a custom object with statistics for the trials
Function.prototype.time =
  function(n,obj) {
    if (n < 1)
      return;
    var o = {f:this, a:[], n:n, mean:0, s:0, sd: 0};
    for(var i=2; i<arguments.length; i++)
      o.a.push(arguments[i]);
    var t1,t2;
    var t = ts = 0;
    for(var i=0; i<n; i++) {
      t1 = new Date();
      this.apply(obj,o.a);
      t2 = (new Date()) - t1;
      t += t2;
      ts += t2 * t2;
    }
    o.mean = t / n;
    o.s = n > 1 ? (ts - t * o.mean) / ( n - 1) : 0;
    o.sd = Math.sqrt(o.s);
    o.toString =
      function() {
        return 'Running ' + this.f.getName() + ' ' + this.n + ' times:\n\nMean:\t'+ (this.mean).toFixed(8) + '\nVariance:\t' + (this.s).toFixed(8) + '\nSD:\t' + (this.sd).toFixed(8);
      };
    return o;
  };

// Find the nth fibonacci number
Math.fibonacci =
  function(n) {
    // Golden ratio
    var g = (1 + Math.sqrt(5)) / 2;
    return Math.round((Math.pow(g,n) - Math.pow(-g,-n)) / Math.sqrt(5));
  };

// Return the logarithm of n using base b
function logN(x,b) {
        if (b == 2)
          return Math.LOG2E * Math.log(x);
        if (b == 10)
          return Math.LOG10E * Math.log(x);
        return Math.log(x) / Math.log(b);
      };

// Return the nth root of x
Math.nRoot =
  function(x,n) {
    var y = Math.log(x) / n;
    return Math.pow(Math.E,y);
  };

// Return a random integer in the interval [0,n]
Math.randomN =
  function(n) {
    return (Math.random() * (n + 1)) | 0;
  };

// Return true if number is a float
Number.prototype.isFloat =
      function() {
        return /\./.test(this.toString());
      };

// Return true if number is an integer
Number.prototype.isInteger =
      function() {
        return Math.floor(this) == this ? true : false;
      };

// Return the hexidecimal string representation of an integer
    Number.prototype.toHex =
      function() {
        // Only convert integers
        if (!this.isInteger())
          throw new Error('Number is not an integer');
        // Can't assign to 'this' so we must copy
        var d = this;
        // Quotient and remainder
        var q = r = null
        // Return value
        var s = '';
        do {
          q = Math.floor(d / 16);
          r = d % 16;
          // If r > 9 then get correct letter (A-F)
          s = r < 10 ? r + s : String.fromCharCode(r + 55) + s;
          d = q;
        }
        while(q)
        return s;
      };

// Convert HTML breaks to newline
String.prototype.br2nl =
  function() {
    return this.replace(/<br\s*\/?>/mg,"\n");
  };

/*
Calculate the Levensthein distance (LD) of two strings
Algorithm is taken from http://www.merriampark.com/ld.htm. The algorithm is considered to be public domain.
1
  Set n to be the length of s.
  Set m to be the length of t.
  If n = 0, return m and exit.
  If m = 0, return n and exit.
  Construct a matrix containing 0..m rows and 0..n columns.  
2 
  Initialize the first row to 0..n.
  Initialize the first column to 0..m.
3
  Examine each character of s (i from 1 to n). 
4
  Examine each character of t (j from 1 to m). 
5
  If s[i] equals t[j], the cost is 0.
  If s[i] doesn't equal t[j], the cost is 1. 
6
  Set cell d[i,j] of the matrix equal to the minimum of:
  a. The cell immediately above plus 1: d[i-1,j] + 1.
  b. The cell immediately to the left plus 1: d[i,j-1] + 1.
  c. The cell diagonally above and to the left plus the cost: d[i-1,j-1] + cost.
7
  After the iteration steps (3, 4, 5, 6) are complete, the distance is found in cell d[n,m].
*/
String.prototype.levenshtein=
  function(t) {
    // ith character of s
    var si; 
    // cost
    var c;
    // Step 1
    var n = this.length;
    var m = t.length;
    if (!n)
      return m;
    if (!m)
      return n;
    // Matrix
    var mx = [];
    // Step 2 - Init matrix
    for (var i=0; i<=n; i++) {
      mx[i] = [];
      mx[i][0] = i;
    }
    for (var j=0; j<=m; j++)
      mx[0][j] = j;
    // Step 3 - For each character in this
    for (var i=1; i<=n; i++) {
      si = this.charAt(i - 1);
      // Step 4 - For each character in t do step 5 (si == t.charAt(j - 1) ? 0 : 1) and 6
      for (var j=1; j<=m; j++)
        mx[i][j] = Math.min(mx[i - 1][j] + 1, mx[i][j - 1] + 1, mx[i - 1][j - 1] + (si == t.charAt(j - 1) ? 0 : 1));
    }
    // Step 7
    return mx[n][m];
  };

// Convert HTML whitespace to normal whitespace
String.prototype.nbsp2s =
  function() {
    return this.replace(/ ?/mg," ");
  };

// Count the number of times a substring is in a string.
String.prototype.substrCount =
  function(s) {
    return this.length && s ? (this.split(s)).length - 1 : 0;
  };

// Return a new string without leading and trailing whitespace
// Double spaces whithin the string are removed as well
String.prototype.trimAll =
  function() {
    return this.replace(/^\s+|(\s+(?!\S))/mg,"");
  };

// Return a new string without leading whitespace
String.prototype.trimLeft =
  function() {
    return this.replace(/^\s+/,"");
  };

// Return a new string without trailing whitespace
String.prototype.trimRight =
  function() {
    return this.replace(/\s+$/,"");
  };

// Insert a node after another node
HTMLElement.prototype.insertAfter =
  function(newNode, refNode) {
    return refNode.nextSibling ? this.insertBefore(newNode, refNode.nextSibling) : this.appendChild(newNode);
  };

// Default name/value pair seperator is '&'
Cookie.valueSeparator = '&';

// Get absolute position of element
HTMLElement.prototype.getPosition =
  function() {
    var o = {x:0,y:0};
    var n = this;
    do {
      o.x += n.offsetLeft;
      o.y += n.offsetTop;
      n = n.offsetParent;
    }
    while(n)
      return o;
  };

// Set an error handler for a function
Function.prototype.setErrorHandler =
  function(f,v) {
    // Throw error if no user function is provided
    if (!f)
      throw new Error('Function.setErrorHandler: No function provided.');
    var that = this;
    // New function to return
    var g = 
      function() {
        try {
          var a = [];
          for(var i=0; i<arguments.length; i++)
            a.push(arguments[i]);
          that.apply(null,a);
        }
        catch(e) {
          return f(g,e,v);
        }
      };
    // Save ref. to original function
    g.old = this;
    return g;
  };

// Check whether XMLHttpRequest is supported
XHR.isEnabled =
  function() {
    return XHR.factory ? true : false;
  };

// Names of the week days
Date.nameOfDays = ['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday'];

// Return a new string without leading and trialing whitespace
String.prototype.trim =
  function() {
    return this.replace(/^\s+(.*?)\s+$/,'$1');
  };

// Retry a request
XHR.prototype.retry =
  function() {
    this._onretry();
  };

// http://www.w3.org/TR/XMLHttpRequest/
// Constructor
function XHR(timeout, retries) {
  if (!XHR.factory)
    throw new Error('XHR: XMLHttpRequest not supported');
  // Actual XMLHttpRequest object
  this.req = XHR.factory();
  // Pseudo readyState property. Used to work around second state of 1 in IE and Firefox
  this._readyState = XHR.READYSTATE_UNINITIALIZED;
  // Work around for IE and Firefox
  this.aborted = false;
  // Set onreadystatechange
  var that = this;
  this.req.onreadystatechange = function(){that._onreadystatechange();};
  // Timeout for reuest in milliseconds. Default value is 30 seconds
  this.timeout = timeout ? timeout : 30000;
  // Ref. to opaque value returned by window.setTimeout for clearing setTimeout
  this.timeoutRef = null;
  // Parameters from open and send call for recreating request when retrying.
  this.retryParams = {};
  // Number of retires. 0 is default
  this.retries = retries ? retries : 0;
}

// Request id. Set and incremented when request is sent
XHR.ridCounter = 0;

// readyState constants
XHR.READYSTATE_UNINITIALIZED = 0;
XHR.READYSTATE_OPEN = 1;
XHR.READYSTATE_SENT = 2;
XHR.READYSTATE_RECIEVING = 3;
XHR.READYSTATE_LOADED = 4;

// Define factory function
// Execute as anomymous function. Only executed once
(function() {
  try {
    // Provoke error if XMLHttpRequest does not exist
    new XMLHttpRequest();
    XHR.factory =
      function() {
        return new XMLHttpRequest();
      };
  }
  catch(e) {  
    try {
      // Provoke error if ActiveXObject("Msxml2.XMLHTTP") does not exist
      new ActiveXObject("Msxml2.XMLHTTP");
      XHR.factory =
        function() {
          return new ActiveXObject("Msxml2.XMLHTTP");
        };
    }
    catch(e) {
      try {
        // Provoke error if ActiveXObject("Microsoft.XMLHTTP") does not exist
        new ActiveXObject("Microsoft.XMLHTTP");
        XHR.factory =
          function() {
            return new ActiveXObject("Microsoft.XMLHTTP");
          };
      }
      catch(e) {
        XHR.factory = null;
      }
    }
  }
})();
// Generic onreadystatechange for all request
XHR.prototype._onreadystatechange =
  function() {
    // Work around for duplicate state 1 for IE and Firefox
    if (this._readyState == XHR.READYSTATE_OPEN && this.req.readyState == XHR.READYSTATE_OPEN)
      return;
    this._readyState = this.req.readyState;
    // Work around for request.abort triggering change to readystate 4
    // in IE and Firefox
    if (this.req.readyState == XHR.READYSTATE_LOADED && this.aborted)
      return;
    // Switch on request object readystate
    switch (this.req.readyState) {
      // open() not called yet
      case XHR.READYSTATE_UNINITIALIZED:
        this.aborted = false;
        break;
      // open() called, send() not called yet
      case XHR.READYSTATE_OPEN:
        // Set request ID
        this.rid = XHR.ridCounter++;
        // User defined event handler for state
        if (this.onreadystateopen)
          this.onreadystateopen();
        break;
      // send() called, no respons yet
      case XHR.READYSTATE_SENT:
        // Code placed here is *not* executed until repsonse is recieved.
        // This is a bug in IE, Firefox and Safari. It works correctly in Opera 9
        // User defined event handler is moved to XHR.prototype.send function
        break;
      // Recieving respons
      case XHR.READYSTATE_RECIEVING:
        // User defined event handler for state
        if (this.onreadystaterecieving)
          this.onreadystaterecieving();
        break;
      // Response complete
      case XHR.READYSTATE_LOADED:
        // Clear timeout if exist
        if (this.timeoutRef) {
          window.clearTimeout(this.timeoutRef);
          this.timeoutRef = null;
        }
        // Get transmission time
        this.time = (new Date()).getTime() - this.time;
        // User defined event handler for state
        if (this.onreadystateloaded)
          this.onreadystateloaded();
        // Response has been handled. Any cleanup code goes here
        // Break possible circular ref. between XHR and XMLHttpRequest objects
        this.breakRef();
        break
      default:
        break;
    }
  };

// Break cicular reference between XHR and XMLHttpRequest object
XHR.prototype.breakRef =
  function() {
    this.req.onreadystatechange = null;
    this.req = null;
  };

// Generic event function for timeout
XHR.prototype._ontimeout =
  function() {
    // First action should be to abort. Thus ontimeout is *always* fired after onabort
    this.abort();
    // Clear window.setTimeout opaque value
    this.timeoutRef = null;
    // User event handler for timeout if provided
    if (this.ontimeout)
      this.ontimeout();
    // Retry if there are any retries left
    if (this.retries > 0)
      this.retry();
  };

// Generic event function for retry
XHR.prototype._onretry =
  function() {
    this.retries--;
    // User event handler for retrying if provided
    if (this.onretry)
      this.onretry();
    // Reset XHR obeject (don't know why we have to make a new XMLHttpRequest object)
    this.reset();
    // Re-open command
    this.open(this.retryParams.method, this.retryParams.url, this.retryParams.async, this.retryParams.username, this.retryParams.password);
    // Re-send command
    this.send(this.retryParams.body);
  };

// Generic event function for abort
XHR.prototype._onabort =
  function() {
    this.aborted = true;
    // Cancel request. This will trigger a change in readystate to 4 in IE and Firefox
    // Safari does not reset readystate to 0
    this.req.abort();
    // User event handler for abortion if provided
    if (this.onabort)
      this.onabort();
  };

// Reset XHR object prior to resending request
XHR.prototype.reset =
  function() {
    // Break circular reference
    this.breakRef();
    // Reset workaround properties
    this.aborted = false;
    this._readyState = XHR.READYSTATE_UNINITIALIZED;
    // Create new XMLHttpRequest instance
    this.req = XHR.factory();
    // Bind onreadystatechange event handler
    var that = this;
    this.req.onreadystatechange = function(){that._onreadystatechange();};
  };

// Open a request
XHR.prototype.open =
  function(method, url, async, username, password) {
    // Save parameters for retry
    this.retryParams.method = method;
    this.retryParams.url = url;
    this.retryParams.async = async;
    this.retryParams.username = username;
    this.retryParams.password = password;
    // Open request
    this.req.open(method, url, async, username, password);
  };

// Send a request
XHR.prototype.send = 
  function(body) {
    if (body === undefined)
      body = null;
    // Save parameters for retry
    this.retryParams.body = body;
    // Set timestamp of request. Have to be done before readyState = 3
    this.time = (new Date()).getTime();
    // setTimeout must be called before readyState = 3
    var that = this;
    // Function arg to setTimeout must be wrapped in anonymous function
    // otherwise 'this' is not resolved correctly
    this.timeoutRef = window.setTimeout(function(){that._ontimeout();}, this.timeout);
    // Send request
    this.req.send(body);
    // User defined event handler for state
    // readyState 2 is handled wrong by IE, Firefox and Safari
    // so functionality must be placed here
    if (this.onreadystatesent)
      this.onreadystatesent();
  };

// Abort a request
XHR.prototype.abort =
  function() {
    // Generic event handler for abortion
    this._onabort();
  };

// Mapping of native getAllResponseHeaders
XHR.prototype.getAllResponseHeaders =
  function() {
    return this.req.getAllResponseHeaders();
  };

// Mapping of native getResponseHeader
XHR.prototype.getResponseHeader =
  function(s) {
    return this.req.getResponseHeader(s);
  };

// Mapping of native setRequestHeader
XHR.prototype.setRequestHeader =
  function(p,v) {
    return this.req.setRequestHeader(p,v);
  };

// Mostly for debugging
XHR.prototype.toString =
  function() {
    var s = 'XHR Info\n--------\n';
    s += 'rid: ' + this.rid + '\n'; 
    s += 'timeout: ' + this.timeout + '\n';
    s += 'retries: ' + this.retries + '\n';
    s += 'time: ' + this.time + '\n';
    s += 'aborted: ' + this.aborted + '\n';
    s += 'method: ' + this.retryParams.method + '\n';
    s += 'url: ' + this.retryParams.url + '\n';
    s += 'async: ' + this.retryParams.async + '\n';
    s += 'username: ' + this.retryParams.username + '\n';
    s += 'password: ' + this.retryParams.password + '\n';
    s += 'body: ' + this.retryParams.body + '\n';
    s += 'readyState: ' + this.req.readyState + '\n';
    return s;
  };

// Remove an error handler previously set with Function.setErrorHandler
Function.prototype.removeErrorHandler =
  function() {
    return this.old ? this.old : this;
  };

Error.lineSeparator = '\n-----------------------\n';

// Get stacktrace
Error.getStackTrace =
  function(f,e,ver) {
    var stack = '';
    // If IE
    if (document.createEventObject) {
      var j = 0;
      var tmp = dump = null;
      var a = null;
      //var f = arguments.callee.caller;
      while(f) {
        stack += 'Stack count: ' + (j++) + '\nFunction: ';
        if (ver > 0) {
          // Get parameter names
          a = f.getArguments();
          dump = '';
          tmp = '';
          // Get values for parameter names
          for(var i=0; i<a.length; i++) {
            if (!a[i])
                a[i] = 'arg' + i;
            if (f.arguments && f.arguments[i] !== undefined) {
              // If argument is a string add quotes
              if (f.arguments[i].constructor == String)
                tmp += a[i] + ':\'' + f.arguments[i] + '\', ';
              // If argument value is another simple type
              else if (f.arguments[i].constructor == Number || f.arguments[i].constructor == Boolean || f.arguments[i] === null)
                tmp += a[i] + ':' + f.arguments[i] + ', ';
              // Else just log 'type' of object
              else if (f.arguments[i].constructor)
                tmp += a[i] + ':[' + f.arguments[i].constructor.getName + '], ';
              // Event object
              else if (f.arguments[i].srcElement !== undefined)
                tmp += a[i] + ':window.event , ';
              // Unknown - dump entire object
              else {
                tmp += a[i] + ':n/a , ';
                if (ver > 2) {
                  dump += 'Enumeration of ' + a[i] + ':\n';
                  for(var p in f.arguments[i])
                    dump += p + ': ' + f.arguments[i][p] + '\n';
                }
              }
            }
            else
              tmp += a[i] + ':undefined, ';
          }
          stack += f.getName() + '(' + tmp.substring(0,tmp.length - 2) + ')\n';
          stack += dump;
        }
        else
          stack += f.getName();
        // If anonymous or verbosity > 1
        if (ver > 1 || (ver > 0 && f.getName() == 'anonymous')) {
          stack += f.getBody();
        }
        // Iterate to next function on callstack
        stack += Error.Separator;
        f = f.caller;
      }
    }
    // Firefox
    else if (e.stack)
      stack = e.stack + Error.Separator;
    // Opera
    else if (e.stacktrace)
      stack = e.stacktrace + Error.Separator;
    // Safari provides no stacktrace
    else
      stack = "Unavailable" + Error.Separator;
    return "Stacktrace" + Error.Separator + stack;
  };

// Create report
Error.report = 
  function(func, error, ver) {
    // Default verbosity level is 1
    ver = ver !== undefined ? ver : 1;
    var evt = null;
    if (func.arguments && func.arguments[0] && func.arguments[0].eventPhase !== undefined)
      evt = func.arguments[0];
    else if (window.event)
      evt = window.event;
    var s = '';
    try {
      s = 'Error info' + Error.Separator;
      s += 'Name: ' + error.name + '\n';
      s += 'Message: ' + error.message + '\n';
      s += 'File: ' + Error.getFilename(error);
      s += 'Line: ' + Error.getLinenumber(error) + Error.Separator;
      s += Error.getClientInfo(ver);
      s += Error.getStackTrace(func,error,ver);
      s += Error.getEventInfo(evt,ver);
    }
    catch(e) {
      s = 'An error occured in the error handler itself. To avoid endless recursion the error handler is terminating.' + (s ? '\nDumping available information on initial error:' + Error.Separator + s : '');
    }
    return 'JSLab Error Report GPLv3 (http://www.jslab.dk/library.error.php)\nVerbosity level: ' + ver + Error.Separator + s + 'End of error report.';
  };

// Get file name
Error.getFilename =
  function(e) {
    var s = '';
    // Firefox
    if (e.fileName)
      s += e.fileName;
    // Safari
    else if (e.sourceURL)
      s += e.sourceURL;
    // Opera
    else if (e.stacktrace) {
      var m = e.message.match(/in\s+(.*?\:\/\/\S+)/m);
      if (m && m[1])
        s += m[1];
      else
        s += 'Couldn\'t extract filename from message. See error message for filename.';
    }
    // IE
    else
      s += location.href + ' (unreliable)';
    return s + '\n';
  };

// Get line number
Error.getLinenumber =
  function(e) {
    var s = '';
    // Firefox
    if (e.lineNumber)
      s += e.lineNumber + '';
    // Safari
    else if (e.line)
      s += e.line;
    // IE/Opera
    else
      s += 'Unavailable. Try message for details.';
    return s + '\n';
  };

// Get info about client
Error.getClientInfo =
  function(ver) {
    var s = 'Client' + Error.lineSeparator;
    if (ver < 1)
      s += 'User agent: ' + window.navigator.userAgent + Error.lineSeparator;
    else
      s += 'Enumeration of window.navigator:\n' + Error.Enumerate(window.navigator,ver);
    return s;
  };

// Get info about event
Error.getEventInfo =
  function(e,ver) {
    var s = 'Event object' + Error.lineSeparator;
    // If event was detected
    if (e) {
      // Get event constructor
      if (e.constructor)
        s += 'Event class: ' + e + '\n';
      else
        s += 'Event class: Unavailable\n';
      // Get object which fired the event
      if (!e.target)
        e.target = e.srcElement;
      if (ver < 1) {
        s += 'Event type: ' + e.type + '\n';
        s += 'Event target (tag name): ' + e.target.nodeName + Error.lineSeparator;
      }
      if (ver > 0) {
        // Get target path
        var n = e.target;
        var tmp = '';
        var a = [];
        while(n) {
          tmp = n.nodeName;
          if (n.id)
            tmp += '#' + n.id;
          if (n.className)
            tmp += '.' + n.className.replace(/\s/g,'___') + '';
          a.push(tmp);
          n = n.parentNode;
        }
        a.reverse();
        a = a.join(' -> ');
        s += 'Event target path: ' + a + '\n';
      }
      if (ver > 1) {
        s += 'Enumeration of event target:\n' + Error.Enumerate(e.target,ver);
        s += 'Enumeration of Event object:\n' + Error.Enumerate(e,ver);
      }
      else
        s += Error.lineSeparator;
    }
    else
      s += 'Unavailable' + Error.Enumerate(e,ver);
    return s;
  };

// Enumerate object for error reporting
Error.Enumerate =
  function(obj,ver) {
    var s = '';
    for(var p in obj) {
      // Don't include native functions
      if (ver > 3)
        s += p + ': ' + obj[p] + '\n';
      else if (ver > 2 && !(obj[p] && typeof obj[p] == 'function' && /native\s+code/im.test(obj[p])))
        s += p + ': ' + obj[p] + '\n';
      else if (!(obj[p] && typeof obj[p] == 'function'))
        s += p + ': ' + obj[p] + '\n';
    }
    return s + Error.lineSeparator;
  };

// Check whether an element has scrollbars
HTMLElement.prototype.hasScrollbars =
  function() {
    // Move scroll bars
    this.scrollLeft++;
    this.scrollTop++;
    // Assume no scrollbars
    var d = 0;
    // Check for x,y
    if (this.scrollLeft > 0 && this.scrollTop > 0) 
      d = 3;
    // Check for x
    else if (this.scrollLeft > 0)
      d = 1;
    // Check for y
    else if (this.scrollTop > 0)
      d = 2;
    this.scrollLeft--;
    this.scrollTop--;
    return d;
  };

// Enable/Disable all inputs in a fieldset
HTMLFieldSetElement.prototype.disable =
  function(b) {
    var c = this.getElementsByTagName('input');
    var l = c.length;
    for(var i=0; i<l; i++)
      c[i].disabled = b;
  };

// Return array of nodes by node type
HTMLElement.prototype.getNodesByType =
  function getTextNodes(nodeType) {
    // For all childnodes in this element
    var a = [];
    var l = this.childNodes.length;
    for(var i=0; i<l; i++) {
      // If correct node type include in matches
      if (this.childNodes[i].nodeType == nodeType)
        a.push(this.childNodes[i]);
      // Only recurse on Node.ELEMENT_NODE (1)
      if (this.childNodes[i].nodeType == 1)
        a = a.concat(this.childNodes[i].getNodesByType(nodeType));
    }
    return a;
  };

// Parse a string to a date. Modelled after the PHP function strtotime      
Date.parse =
  function(s,rd) {
    // If s is not specified
    if (!s)
      return new Date();
    // Trim s
    s = s.replace(/^\s+|\s+$/g,"");
    // If no reference date is provided the reference is now
    if (!rd)
      rd = new Date();
    // Get keywords used for relative parsing
    var t = '';
    for(var p in Date.parse.keywords)
      t += p + '|';
    t = t.substring(0, t.length-1);
    var rgx = new RegExp(t,'i');
    // Determine to parse absolute or relative
    if (rgx.test(s))
      return Date.parse.relative(s, rd);
    else
      return Date.parse.absolute(s, rd);
  };

// Parse absolute date
Date.parse.absolute =
  function(s, rd) {
    var r = null, y = null, m = null, d = null, h = null, mi = null, se = null, ms = null;
    // Date format 1972-09-24, 72-9-24, 72-09-24
    if (r = s.match(/^(\d{4}|\d{2})-(\d{1,2})-(\d{1,2})/)) {
      // Year
      y = r[1] * 1;
      // Month - JS months is zero based
      m = r[2] * 1 - 1;
      // Date
      d = r[3] * 1;
    }
    // US date format 9/24/72 (m/d/y)
    else if (r = s.match(/^(\d{1,2})\/(\d{1,2})\/(\d{4}|\d{2})/)) {
      // Month
      m = r[1] * 1 - 1;
      // Date
      d = r[2] * 1;
      // Year
      y = r[3] * 1;
    }
    // Month written litteraly
    // Date format 4 September 1972, 24 Sept 72, 24 Sep 72, 24-sep-72
    else if (r = s.match(/^(\d{1,2})(\s*|-)([a-z]{3,})((\s+|-)(\d{4}|\d{2}))?/i)) {
      // Date
      d = r[1] * 1;
      // Month
      var rgx = new RegExp('^' + r[3],'i');
      for(var i=0; i<11; i++) {
        if (rgx.test(Date.nameOfMonths[i])) {
          m = i;
          break;
        }
      }
      if (m === null)
        throw new Error('Date.parse: Unknown month specified litteraly');
      // Year
      if (r[6])
        y = r[6] * 1;
    }
    // Check date
    // Check year
    if (y < 100)
      y = y > 68 && y < 100 ? 1900 + y : 2000 + y;
    else if (y && (y < 1972 || y > 2068))
      throw new Error('Date.parse: Year out of range. Valid input is 1972 through 2068');
    // Check month
    if (m && (m < 0 || m > 11))
      throw new Error('Date.parse: Month out of range. Valid input is 01 through 12');
    // Check date - don't check for 28/29 in feb etc. Just overflow dates
    if (d && (d < 1 || d > 31))
      throw new Error('Date.parse: Date out of range. Valid input is 01 through 31');
    // Set date
    if (y)
      rd.setFullYear(y);
    if (m !== null)
      rd.setMonth(m);
    if (d)
      rd.setDate(d);
    // ***
    // Parse time
    // ***
    // Old regex used to detect timezone - contains am/pm error
    //if (r = s.match(/(\d{1,2})\:(\d{1,2})(?:(?:\:(\d{1,2})(?:\.(\d{1,3}))?)(?:(am|pm)?))?(?:([\+-])(\d{2})\:?(\d{2}))?/)) {
    if (r = s.match(/(\d{1,2})\:(\d{1,2})(?:(?:\:(\d{1,2})(?:\.(\d{1,3}))?)?(?:\s*(am|pm)?))?/)) {
      /*
      Timezone
      TZ sign is r[6]
      TZ hour is r[7]
      TZ minutes is r[8]
      It doesn't make sense to adjust timezones as the you can't change timezone with JS
      */
      // Hour
      h = r[1] * 1;
      // If am/pm is specified
      if (r[5]) {
        if (h < 1 || h > 12)
          throw new Error('Date.parse: Hour out of range (using am/pm). Valid input is 1 through 12');
        // If am
        if (r[5] == 'am') {
          if (h == 12)
            h = 0;
        }
        // If pm
        else {
          if (h != 12)
            h = h + 12;
        }
      }
      else {
        if (h > 24)
          throw new Error('Date.parse: Hour out of range. Valid input is 00 through 23');
      }
      // Minute
      if (r[2]) {
        mi = r[2] * 1;
        if (mi > 59)
          throw new Error('Date.parse: Minute out of range. Valid input is 00 through 59');
      }
      // Seconds
      if (r[3]) {
        se = r[3] * 1;
        if (se > 59)
          throw new Error('Date.parse: Seconds out of range. Valid input is 00 through 59');
      }
      // Msecs
      if (r[4]) {
        // For whatever reason the multiplication becomes slightly incorrect and have to be ceiled.
        ms = Math.ceil(('1.' + r[4]) * 1000) - 1000;
      }
    }
    // Set time
    if (h !== null)
      rd.setHours(h);
    if (mi !== null)
      rd.setMinutes(mi);
    if (se !== null)
      rd.setSeconds(se);
    if (ms !== null)
      rd.setMilliseconds(ms);
    return rd;
  };

// Parse relative date
Date.parse.relative =
  function(s,rd) {
    // If relative date is given as a single word - ie. now, today, tomorrow, yesterday, fortnight
    if (/^now|today|tomorrow|fortnight|yesterday$/.test(s)) {
      rd.setDate(rd.getDate() + Date.parse.keywords[s]);
    }
    else {
      var mod;
      var p = /(last|this|next|first|third|fourth|fifth|sixth|seventh|eighth|ninth|tenth|eleventh|twelfth|(?:[\+-]?\d+))\s+([a-z]+)(?:\s+(ago))?/g;
      while((r = p.exec(s)) != null) {
        // r[1] is relative number or word
        // r[2] is time interval
        // r[3] is optional 'ago'
        // If modifier is not a number 'ago' does not apply
        if (/(?:[\+-]?\d+)/.test(r[1]))
          mod = !r[3] ? parseInt(r[1]) : -1 * parseInt(r[1]);
        else
          mod = Date.parse.keywords[r[1]];
        // Remove plural s and convert to lower case
        r[2] = r[2].replace(/s$/,'').toLowerCase();
        // Switch on interval
        switch(r[2]) {
          case 'year':
            rd.setFullYear(rd.getFullYear() + mod);
            break;
          case 'month':
            rd.setMonth(rd.getMonth() + mod);
            break;
          case 'week':
            rd.setDate(rd.getDate() + mod * 7);
            break;
          case 'day':
            rd.setDate(rd.getDate() + mod);
            break;
          case 'hour':
            rd.setHours(rd.getHours() + mod);
            break;
          case 'minute':
            rd.setMinutes(rd.getMinutes() + mod);
            break;
          case 'second':
            rd.setSeconds(rd.getSeconds() + mod);
            break;
          default:
            // Check for weekdays
            var rgx = new RegExp('^' + r[2],'i');
            for(var i=0; i<7; i++) {
              if (rgx.test(Date.nameOfDays[i]))
                break;
            }
            // If weekday exists
            if (i < 7) {
              var d = rd.getISODay() - 1;
              // If weekday is in the future
              if (i > d)
                rd.setDate(rd.getDate() + (i - d) + ((mod - 1) * 7));
              else
                rd.setDate(rd.getDate() + (i - d) + ((mod - 1) * 7) + 7);
            }
            else
              throw new Error('Date.parse: Unknown keyword in input');
            break;
        }
      }
    }
    return rd;
  };

// Valid keywords in input string
Date.parse.keywords =
  {
    // Absolute. Numbers are offsets in days
    now: 0,
    today: 0,
    tomorrow: 1,
    fortnight: 14,
    yesterday: -1,
    // Relative
    last: -1,
    'this': 1,
    next: 1,
    // Ordinal numbers
    first: 1,
    third: 3,
    fourth: 4,
    fifth: 5,
    sixth: 6,
    seventh: 7,
    eighth: 8,
    ninth: 9,
    tenth: 10,
    eleventh: 11,
    twelfth: 12,
    // Intervals
    second: null,
    minute: null,
    hour: null,
    day: null,
    week: null,
    month: null,
    year: null
  };

// Memoize (cache) results of a function call
Function.prototype.memoize =
  function() {
    // If no calls yet
    if (!this.results)
      this.results = {};
    // Convert arguments object to array
    var args = Array.prototype.slice.call(arguments);
    // If results exist
    if (this.results[args])
      return this.results[args];
    // Else compute and store result
    this.results[args] = this.apply(null, args);
    // Return result of original function
    return this.results[args];
  };

