package com.piiottron.config;

public final class Keys {

	/**
	 * Connection timeout value in seconds. Because sometimes there is no way to
	 * detect lost TCP connection old connections stay in open state. On most
	 * systems there is a limit on number of open connection, so this leads to
	 * problems with establishing new connections when number of devices is high or
	 * devices data connections are unstable.
	 */
	public static final ConfigSuffix PROTOCOL_TIMEOUT = new ConfigSuffix(".timeout", Integer.class);

	/**
	 * Server wide connection timeout value in seconds. See protocol timeout for
	 * more information.
	 */
	public static final ConfigKey SERVER_TIMEOUT = new ConfigKey("server.timeout", Integer.class);

	/**
	 * Address for uploading aggregated anonymous usage statistics. Uploaded
	 * information is the same you can see on the statistics screen in the web app.
	 * It does not include any sensitive (e.g. locations).
	 */
	public static final ConfigKey SERVER_STATISTICS = new ConfigKey("server.statistics", Boolean.class);

	/**
	 * Enable events subsystem. Flag to enable all events handlers.
	 */
	public static final ConfigKey EVENT_ENABLE = new ConfigKey("event.enable", Boolean.class);

	/**
	 * If true, the event is generated once at the beginning of overspeeding period.
	 */
	public static final ConfigKey EVENT_OVERSPEED_NOT_REPEAT = new ConfigKey("event.overspeed.notRepeat",
			Boolean.class);

	/**
	 * Minimal over speed duration to trigger the event. Value in seconds.
	 */
	public static final ConfigKey EVENT_OVERSPEED_MINIMAL_DURATION = new ConfigKey("event.overspeed.minimalDuration",
			Long.class);

	/**
	 * Relevant only for geofence speed limits. Use lowest speed limits from all
	 * geofences.
	 */
	public static final ConfigKey EVENT_OVERSPEED_PREFER_LOWEST = new ConfigKey("event.overspeed.preferLowest",
			Boolean.class);

	/**
	 * Do not generate alert event if same alert was present in last known location.
	 */
	public static final ConfigKey EVENT_IGNORE_DUPLICATE_ALERTS = new ConfigKey("event.ignoreDuplicateAlerts",
			Boolean.class);

	/**
	 * List of external handler classes to use in Netty pipeline.
	 */
	public static final ConfigKey EXTRA_HANDLERS = new ConfigKey("extra.handlers", String.class);

	/**
	 * Enable positions forwarding to other web server.
	 */
	public static final ConfigKey FORWARD_ENABLE = new ConfigKey("forward.enable", Boolean.class);

	/**
	 * URL to forward positions. Data is passed through URL parameters. For example,
	 * {uniqueId} for device identifier, {latitude} and {longitude} for coordinates.
	 */
	public static final ConfigKey FORWARD_URL = new ConfigKey("forward.url", String.class);

	/**
	 * Additional HTTP header, can be used for authorization.
	 */
	public static final ConfigKey FORWARD_HEADER = new ConfigKey("forward.header", String.class);

	/**
	 * Boolean value to enable forwarding in JSON format.
	 */
	public static final ConfigKey FORWARD_JSON = new ConfigKey("forward.json", Boolean.class);

	/**
	 * Boolean value to enable URL parameters in json mode. For example, {uniqueId}
	 * for device identifier, {latitude} and {longitude} for coordinates.
	 */
	public static final ConfigKey FORWARD_URL_VARIABLES = new ConfigKey("forward.urlVariables", Boolean.class);

	/**
	 * Position forwarding retrying enable. When enabled, additional attempts are
	 * made to deliver positions. If initial delivery fails, because of an
	 * unreachable server or an HTTP response different from '2xx', the software
	 * waits for 'forward.retry.delay' milliseconds to retry delivery. On subsequent
	 * failures, this delay is duplicated. If forwarding is retried for
	 * 'forward.retry.count', retrying is canceled and the position is dropped.
	 * Positions pending to be delivered are limited to 'forward.retry.limit'. If
	 * this limit is reached, positions get discarded.
	 */
	public static final ConfigKey FORWARD_RETRY_ENABLE = new ConfigKey("forward.retry.enable", Boolean.class);

	/**
	 * Position forwarding retry first delay in milliseconds. Can be set to anything
	 * greater than 0. Defaults to 100 milliseconds.
	 */
	public static final ConfigKey FORWARD_RETRY_DELAY = new ConfigKey("forward.retry.delay", Integer.class);

	/**
	 * Position forwarding retry maximum retries. Can be set to anything greater
	 * than 0. Defaults to 10 retries.
	 */
	public static final ConfigKey FORWARD_RETRY_COUNT = new ConfigKey("forward.retry.count", Integer.class);

	/**
	 * Position forwarding retry pending positions limit. Can be set to anything
	 * greater than 0. Defaults to 100 positions.
	 */
	public static final ConfigKey FORWARD_RETRY_LIMIT = new ConfigKey("forward.retry.limit", Integer.class);

	/**
	 * Boolean flag to enable or disable position filtering.
	 */
	public static final ConfigKey FILTER_ENABLE = new ConfigKey("filter.enable", Boolean.class);

	/**
	 * Filter invalid (valid field is set to false) positions.
	 */
	public static final ConfigKey FILTER_INVALID = new ConfigKey("filter.invalid", Boolean.class);

	/**
	 * Filter zero coordinates. Zero latitude and longitude are theoretically valid
	 * values, but it practice it usually indicates invalid GPS data.
	 */
	public static final ConfigKey FILTER_ZERO = new ConfigKey("filter.zero", Boolean.class);

	/**
	 * Filter duplicate records (duplicates are detected by time value).
	 */
	public static final ConfigKey FILTER_DUPLICATE = new ConfigKey("filter.duplicate", Boolean.class);

	/**
	 * Filter records with fix time in future. The values is specified in seconds.
	 * Records that have fix time more than specified number of seconds later than
	 * current server time would be filtered out.
	 */
	public static final ConfigKey FILTER_FUTURE = new ConfigKey("filter.future", Long.class);

	/**
	 * Filter positions with accuracy less than specified value in meters.
	 */
	public static final ConfigKey FILTER_ACCURACY = new ConfigKey("filter.accuracy", Integer.class);

	/**
	 * Filter cell and wifi locations that are coming from geolocation provider.
	 */
	public static final ConfigKey FILTER_APPROXIMATE = new ConfigKey("filter.approximate", Boolean.class);

	/**
	 * Filter positions with exactly zero speed values.
	 */
	public static final ConfigKey FILTER_STATIC = new ConfigKey("filter.static", Boolean.class);

	/**
	 * Filter records by distance. The values is specified in meters. If the new
	 * position is less far than this value from the last one it gets filtered out.
	 */
	public static final ConfigKey FILTER_DISTANCE = new ConfigKey("filter.distance", Integer.class);

	/**
	 * Filter records by Maximum Speed value in knots. Can be used to filter jumps
	 * to far locations even if they're marked as valid. Shouldn't be too low. Start
	 * testing with values at about 25000.
	 */
	public static final ConfigKey FILTER_MAX_SPEED = new ConfigKey("filter.maxSpeed", Integer.class);

	/**
	 * Filter position if time from previous position is less than specified value
	 * in seconds.
	 */
	public static final ConfigKey FILTER_MIN_PERIOD = new ConfigKey("filter.minPeriod", Integer.class);

	/**
	 * Time limit for the filtering in seconds. If the time difference between last
	 * position and a new one is more than this limit, the new position will not be
	 * filtered out.
	 */
	public static final ConfigKey FILTER_SKIP_LIMIT = new ConfigKey("filter.skipLimit", Long.class);

	/**
	 * Enable attributes skipping. Attribute skipping can be enabled in the config
	 * or device attributes.
	 */
	public static final ConfigKey FILTER_SKIP_ATTRIBUTES_ENABLE = new ConfigKey("filter.skipAttributes.enable",
			Boolean.class);

	/**
	 * Override device time. Possible values are 'deviceTime' and 'serverTime'
	 */
	public static final ConfigKey TIME_OVERRIDE = new ConfigKey("time.override", String.class);

	/**
	 * List of protocols for overriding time. If not specified override is applied
	 * globally. List consist of protocol names that can be separated by comma or
	 * single space character.
	 */
	public static final ConfigKey TIME_PROTOCOLS = new ConfigKey("time.protocols", String.class);

	/**
	 * Replaces coordinates with last known if change is less than a
	 * 'coordinates.minError' meters or more than a 'coordinates.maxError' meters.
	 * Helps to avoid coordinates jumps during parking period or jumps to zero
	 * coordinates.
	 */
	public static final ConfigKey COORDINATES_FILTER = new ConfigKey("coordinates.filter", Boolean.class);

	/**
	 * Distance in meters. Distances below this value gets handled like explained in
	 * 'coordinates.filter'.
	 */
	public static final ConfigKey COORDINATES_MIN_ERROR = new ConfigKey("coordinates.minError", Integer.class);

	/**
	 * Distance in meters. Distances above this value gets handled like explained in
	 * 'coordinates.filter', but only if Position is also marked as 'invalid'.
	 */
	public static final ConfigKey COORDINATES_MAX_ERROR = new ConfigKey("coordinates.maxError", Integer.class);

	/**
	 * Enable to save device IP addresses information. Disabled by default.
	 */
	public static final ConfigKey PROCESSING_REMOTE_ADDRESS_ENABLE = new ConfigKey("processing.remoteAddress.enable",
			Boolean.class);

	/**
	 * Enable engine hours calculation on the server side. It uses ignition value to
	 * determine engine state.
	 */
	public static final ConfigKey PROCESSING_ENGINE_HOURS_ENABLE = new ConfigKey("processing.engineHours.enable",
			Boolean.class);

	/**
	 * Enable copying of missing attributes from last position to the current one.
	 * Might be useful if device doesn't send some values in every message.
	 */
	public static final ConfigKey PROCESSING_COPY_ATTRIBUTES_ENABLE = new ConfigKey("processing.copyAttributes.enable",
			Boolean.class);

	/**
	 * Enable computed attributes processing.
	 */
	public static final ConfigKey PROCESSING_COMPUTED_ATTRIBUTES_ENABLE = new ConfigKey(
			"processing.computedAttributes.enable", Boolean.class);

	/**
	 * Enable computed attributes processing.
	 */
	public static final ConfigKey PROCESSING_COMPUTED_ATTRIBUTES_DEVICE_ATTRIBUTES = new ConfigKey(
			"processing.computedAttributes.deviceAttributes", Boolean.class);

	/**
	 * Override latitude sign / hemisphere. Useful in cases where value is incorrect
	 * because of device bug. Value can be N for North or S for South.
	 */
	public static final ConfigKey LOCATION_LATITUDE_HEMISPHERE = new ConfigKey("location.latitudeHemisphere",
			Boolean.class);

	/**
	 * Override longitude sign / hemisphere. Useful in cases where value is
	 * incorrect because of device bug. Value can be E for East or W for West.
	 */
	public static final ConfigKey LOCATION_LONGITUDE_HEMISPHERE = new ConfigKey("location.longitudeHemisphere",
			Boolean.class);

	private Keys() {
	}

}
