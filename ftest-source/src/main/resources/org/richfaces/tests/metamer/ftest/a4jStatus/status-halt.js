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
var Metamer = {
	halts : new Array(),
	sequenceId : 0,
	haltIterator : 0,
	haltEnabled : false,
	passes : 0,
	wait : function(metamerHalt) {
		Metamer.halts.push(metamerHalt);
		Metamer._wait(metamerHalt.id);
	},
	_wait : function(id) {
		var metamerHalt = Metamer.halts[id];
		if (metamerHalt.halt) {
			setTimeout("Metamer._wait(" + id + ")", 100);
		} else {
			metamerHalt.callback(metamerHalt.xhr, metamerHalt.content);
		}
	},
	unhalt : function() {
		var metamerHalt = Metamer.halts[Metamer.haltIterator];
		Metamer.haltIterator += 1;
		metamerHalt.halt = false;
	},
	isHalted : function() {
		return Metamer.sequenceId > Metamer.haltIterator;
	},
	waitForHalt : function() {
		return Metamer.sequenceId == 1 + Metamer.haltIterator;
	}
};

var MetamerHalt = function(xhr, content, callback) {
	this.halt = true;
	this.xhr = xhr;
	this.content = content;
	this.callback = callback;
	this.id = Metamer.sequenceId++;
}

Metamer.XHRWrapperInjection = {
	send : RichFacesSelenium.XHRWrapper.prototype.send
};

RichFacesSelenium.XHRWrapper.prototype.send = function(content) {
	if (Metamer.haltEnabled) {
		var metamerHalt = new MetamerHalt(this, content, function(xhr, content1) {
			Metamer.XHRWrapperInjection.send.call(xhr, content1);
		});
		Metamer.wait(metamerHalt);
	} else {
		Metamer.XHRWrapperInjection.send.call(this, content);
	}
};