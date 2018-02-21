(function(win) {
    var Map = function() {
        this.count = 0;
        this.entrySet = {};
    };

    var proto = Map.prototype;

    proto.size = function() {
        return this.count;
    };

    proto.isEmpty = function() {
        return this.count === 0;
    };

    proto.containsKey = function(key) {
        if (this.isEmpty()) {
            return false;
        }

        for ( var prop in this.entrySet) {
            if (prop === key) {
                return true;
            }
        }

        return false;
    };

    proto.containsValue = function(value) {
        if (this.isEmpty()) {
            return false;
        }

        for ( var key in this.entrySet) {
            if (this.entrySet[key] === value) {
                return true;
            }
        }

        return false;
    };

    proto.get = function(key) {
        if (this.isEmpty()) {
            return null;
        }

        if (this.containsKey(key)) {
            return this.entrySet[key];
        }

        return null;
    };

    proto.put = function(key, value) {
        this.entrySet[key] = value;
        this.count++;
    };

    proto.remove = function(key) {
        if (this.containsKey(key)) {
            delete this.entrySet[key];
            this.count--;
        }
    };

    proto.putAll = function(map) {
        for ( var key in map.entrySet) {
            this.put(key, map.entrySet[key]);
        }
    };

    proto.clear = function() {
        for ( var key in this.entrySet) {
            this.remove(key);
        }
    };

    proto.values = function() {
        var result = [];

        for ( var key in this.entrySet) {
            result.push(this.entrySet[key]);
        }

        return result;
    };

    proto.keySet = function() {
        var result = [];

        for ( var key in this.entrySet) {
            result.push(key);
        }

        return result;
    };

    proto.toString = function() {
        var result = [];
        for ( var key in this.entrySet) {
            result.push(key + ":" + this.entrySet[key]);
        }

        return "{" + result.join() + "}";
    };

    proto.valueOf = function() {
        return this.toString();
    };

    win.Map = Map;
})(window);