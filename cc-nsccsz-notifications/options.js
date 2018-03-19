// Copyright (c) 2011 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

/*
  Grays out or [whatever the opposite of graying out is called] the option
  field.
*/
function ghost(isDeactivated) {
  options.style.color = isDeactivated ? 'graytext' : 'black';
  options.frequency.disabled = isDeactivated; // The control manipulability.
}

window.addEventListener('load', function() {
  // Initialize the option controls.
  console.log( 'qwop cc : initialize the option controls  ');


  options.isActivated.checked = JSON.parse(localStorage.isActivated);
                                         // The display activation.
  options.frequency.value = localStorage.frequency;
                                         // The display frequency, in minutes.
	
  if (!options.isActivated.checked) { ghost(true); }
	

  // Set the display activation and frequency.
  options.isActivated.onchange = function() {
    localStorage.isActivated = options.isActivated.checked;
    ghost(!options.isActivated.checked);
  };

  options.frequency.onchange = function() {
    localStorage.frequency = options.frequency.value;
  };
  
  // load value.
  options.cc_username.value = localStorage.cc_username;
  options.cc_password.value = localStorage.cc_password;

  options.cc_username.onchange = function() {
	localStorage.cc_username = options.cc_username.value;
  };
  options.cc_password.onchange = function() {
	localStorage.cc_password = options.cc_password.value;
  };
});
