/*
 * JBoss, Home of Professional Open Source
 * Copyright ${year}, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
var SendHalt = function(xhr, content, callback) {
	SendHalt._instances.push(this);
	this.halt = true;
	this.xhr = xhr;
	this.content = content;
	this.callback = callback;
	this.id = SendHalt._instances.length - 1;
}

SendHalt._instances = new Array();
SendHalt._haltCounter = 0;
SendHalt._enabled = false;

SendHalt.setEnabled = function(enabled) {
	SendHalt._enabled = enabled;
}

SendHalt.isEnabled = function(enabled) {
	return SendHalt._enabled;
}

SendHalt.getLastId = function() {
	return SendHalt._instances.length - 1;
}

SendHalt.getHalt = function() {
	if (SendHalt.isHaltAvailable()) {
		SendHalt._haltCounter += 1;
		return SendHalt.getLastId();
	}
	return -1;
}

SendHalt.XHRWrapperInjection = {
	send : RichFacesSelenium.XHRWrapper.prototype.send
}

SendHalt._repeatWait = function(id) {
	var halt = SendHalt._instances[id];
	if (halt.halt) {
		setTimeout("SendHalt._repeatWait(" + id + ")", 100);
	} else {
		halt.callback(halt.xhr, halt.content);
	}
}

SendHalt.prototype.wait = function() {
	SendHalt._repeatWait(this.id);
}

SendHalt.unhalt = function(id) {
	var halt = SendHalt._instances[id];
	halt.halt = false;
}

SendHalt.isHaltAvailable = function() {
	return SendHalt.getLastId() == SendHalt._haltCounter;
}

RichFacesSelenium.XHRWrapper.prototype.send = function(content) {
	if (SendHalt.isEnabled()) {
		var metamerHalt = new SendHalt(this, content, function(xhr, content1) {
			SendHalt.XHRWrapperInjection.send.call(xhr, content1);
		});
		metamerHalt.wait();
	} else {
		SendHalt.XHRWrapperInjection.send.call(this, content);
	}
};