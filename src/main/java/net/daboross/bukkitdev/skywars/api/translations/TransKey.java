/*
 * Copyright (C) 2013-2016 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.skywars.api.translations;

/**
 * Translation keys for SkyWars. This enumeration should contain one key for every individual message in game.
 */
public enum TransKey {

    // commands.join
    CMD_JOIN_DESCRIPTION("commands.join.description", 0),
    CMD_JOIN_CONFIRMATION("commands.join.confirmation", 0),
    CMD_JOIN_ALREADY_QUEUED("commands.join.already-queued", 0),
    CMD_JOIN_IN_GAME("commands.join.in-game", 0),
    // commands.leave
    CMD_LEAVE_DESCRIPTION("commands.leave.description", 0),
    CMD_LEAVE_REMOVED_FROM_QUEUE("commands.leave.removed-from-queue", 0),
    CMD_LEAVE_REMOVED_FROM_GAME("commands.leave.removed-from-game", 0),
    CMD_LEAVE_NOT_IN("commands.leave.not-in-any", 0),
    // commands.setlobby
    CMD_SETLOBBY_DESCRIPTION("commands.setlobby.description", 0),
    CMD_SETLOBBY_CONFIRMATION("commands.setlobby.confirmation", 0),
    CMD_SETLOBBY_IN_GAME("commands.setlobby.in-game", 0),
    // commands.setportal
    CMD_SETPORTAL_DESCRIPTION("commands.setportal.description", 0),
    CMD_SETPORTAL_CONFIRMATION("commands.setportal.confirmation", 0),
    CMD_SETPORTAL_IN_GAME("commands.setportal.in-game", 0),
    // commands.delportal
    CMD_DELPORTAL_DESCRIPTION("commands.delportal.description", 0),
    CMD_DELPORTAL_CONFIRMATION("commands.delportal.confirmation", 1),
    CMD_DELPORTAL_NO_PORTAL_ERROR("commands.delportal.error-no-portals", 0),
    // commands.cancel
    CMD_CANCEL_DESCRIPTION("commands.cancel.description", 0),
    CMD_CANCEL_ARGUMENT("commands.cancel.argument", 0),
    CMD_CANCEL_NO_GAMES_WITH_ID("commands.cancel.no-games-with-id", 1),
    CMD_CANCEL_CONFIRMATION("commands.cancel.confirmation", 1),
    // commands.status
    CMD_STATUS_DESCRIPTION("commands.status.description", 0),
    CMD_STATUS_HEADER("commands.status.header", 0),
    CMD_STATUS_IN_QUEUE("commands.status.in-queue", 1),
    CMD_STATUS_COMMA("commands.status.queue-comma", 0),
    CMD_STATUS_ARENA_HEADER("commands.status.arena-header", 0),
    CMD_STATUS_ARENA_LIST_PREFIX_NO_TEAMS("commands.status.no-teams-arena-list-prefix", 1),
    CMD_STATUS_ARENA_LIST_PREFIX_TEAMS_LEFT("commands.status.arena-teams-left", 3),
    CMD_STATUS_ARENA_TEAM_PREFIX("commands.status.arena-team-prefix", 1),
    CMD_STATUS_ARENA_TEAM_DEAD("commands.status.arena-team-dead", 1),
    // commands.version
    CMD_VERSION_DESCRIPTION("commands.version.description", 0),
    CMD_VERSION_OUTPUT("commands.version.output", 3),
    // commands.lobby
    CMD_LOBBY_DESCRIPTION("commands.lobby.description", 0),
    CMD_LOBBY_CONFIRMATION("commands.lobby.confirmation", 0),
    CMD_LOBBY_IN_GAME("commands.lobby.in-game", 0),
    // commands.cancelall
    CMD_CANCELALL_DESCRIPTION("commands.cancelall.description", 0),
    CMD_CANCELALL_CANCELING("commands.cancelall.canceling-game", 1),
    // commands.report
    CMD_REPORT_DESCRIPTION("commands.report.description", 0),
    CMD_REPORT_START("commands.report.start", 0),
    CMD_REPORT_OUTPUT("commands.report.output", 1),
    // commands.forcestart
    CMD_FORCESTART_DESCRIPTION("commands.forcestart.description", 0),
    CMD_FORCESTART_NOT_ENOUGH("commands.forcestart.not-enough-players", 0),
    // commands.kit
    CMD_KIT_DESCRIPTION("commands.kit.description", 0),
    CMD_KIT_ARGUMENT("commands.kit.argument", 0),
    CMD_KIT_NO_KITS_AVAILABLE("commands.kit.no-kits-available", 0),
    CMD_KIT_UNAVAILABLE_KITS("commands.kit.unavailable-kits", 1),
    CMD_KIT_UNKNOWN_KIT("commands.kit.unknown-kit", 1),
    CMD_KIT_NOT_ENOUGH_MONEY("commands.kit.not-enough-money", 3),
    CMD_KIT_CHOSE_KIT("commands.kit.chose-kit", 1),
    CMD_KIT_CHOSE_KIT_WITH_COST("commands.kit.chose-kit-with-cost", 2),
    CMD_KIT_HOW_TO_REMOVE("commands.kit.how-to-remove", 0),
    CMD_KIT_REMOVED_KIT("commands.kit.removed-kit", 1),
    CMD_KIT_NO_KIT_REMOVED("commands.kit.no-kit-removed", 0),
    CMD_KIT_CURRENT_KIT("commands.kit.current-kit", 1),
    CMD_KIT_CURRENT_KIT_WITH_COST("commands.kit.current-kit-with-cost", 2),
    CMD_KIT_NO_ACCESS("commands.kit.no-access", 1),
    // commands.testkit
    CMD_TESTKIT_DESCRIPTION("commands.testkit.description", 0),
    CMD_TESTKIT_UNKNOWN("commands.testkit.unknown", 1),
    CMD_TESTKIT_APPLIED("commands.testkit.applied", 1),
    // setup-commands.start
    SWS_START_DESCRIPTION("setup-commands.start.description", 0),
    SWS_START_NAME_ARGUMENT("setup-commands.start.argument-name", 0),
    SWS_START_CONFIRMATION("setup-commands.start.confirmation", 1),
    SWS_START_ONE_ARGUMENT("setup-commands.start.one-argument", 0),
    // setup-commands.setpos1
    SWS_SETPOS1_DESCRIPTION("setup-commands.setpos1.description", 0),
    SWS_SETPOS1_POS2_OTHER_WORLD("setup-commands.setpos1.pos2-in-other-world", 0),
    SWS_SETPOS1_CONFIRMATION("setup-commands.setpos1.confirmation", 0),
    // setup-commands.setpos2
    SWS_SETPOS2_DESCRIPTION("setup-commands.setpos2.description", 0),
    SWS_SETPOS2_POS1_OTHER_WORLD("setup-commands.setpos2.pos1-in-other-world", 0),
    SWS_SETPOS2_CONFIRMATION("setup-commands.setpos2.confirmation", 0),
    // setup-commands.addspawn
    SWS_ADDSPAWN_DESCRIPTION("setup-commands.addspawn.description", 0),
    SWS_ADDSPAWN_CONFIRMATION("setup-commands.addspawn.confirmation", 1),
    // setup-commands.save
    SWS_SAVE_DESCRIPTION("setup-commands.save.description", 0),
    SWS_SAVE_SAVING("setup-commands.save.saving", 0),
    SWS_SAVE_SAVED("setup-commands.save.saved", 0),
    SWS_SAVE_FAILED("setup-commands.save.failed", 0),
    // setup-commands.generic
    SWS_NO_FIRST_POSITION("setup-commands.generic.no-first-position", 0),
    SWS_NOT_ENOUGH_SPAWNS("setup-commands.generic.not-enough-spawns", 0),
    SWS_TOO_LATE_SPAWNS_SET("setup-commands.generic.too-late-spawns-set", 0),
    SWS_NO_ARENA_STARTED("setup-commands.generic.no-arena-started", 0),
    SWS_ARENA_ALREADY_STARTED("setup-commands.generic.no-arena-started", 0),
    // setup-commands.createkit
    SWS_CREATEKIT_DESCRIPTION("setup-commands.createkit.description", 0),
    SWS_CREATEKIT_NAME_ARGUMENT_NAME("setup-commands.createkit.argument-name", 0),
    SWS_CREATEKIT_COST_ARGUMENT_NAME("setup-commands.createkit.argument-cost", 0),
    SWS_CREATEKIT_PERMISSION_ARGUMENT_NAME("setup-commands.createkit.argument-permission", 0),
    SWS_CREATEKIT_SAVED("setup-commands.createkit.saved-no-permission", 1),
    SWS_CREATEKIT_SAVED_PERMISSION("setup-commands.createkit.saved-with-permission", 2),
    SWS_CREATEKIT_COST_TO_USE("setup-commands.createkit.cost-to-use", 1),
    SWS_CREATEKIT_KIT_EXISTS("setup-commands.createkit.kit-exists", 1),
    SWS_CREATEKIT_SAVE_FAILED("setup-commands.createkit.save-failed", 0),
    // setup-commands.update-arena
    SWS_UPDATEARENA_DESCRIPTION("setup-commands.update-arena.description", 0),
    SWS_UPDATEARENA_ARENA_ARGUMENT("setup-commands.update-arena.argument-arena", 0),
    SWS_UPDATEARENA_COMPLETED("setup-commands.update-arena.completed", 1),
    SWS_UPDATEARENA_UNKNOWN_ARENA("setup-commands.update-arena.unknown-arena", 1),
    SWS_UPDATEARENA_FAILED("setup-commands.update-arena.failed", 1),
    SWS_UPDATEARENA_BUILTIN("setup-commands.update-arena.failed-built-in", 1),
    // top-commands
    MAIN_CMD_DESCRIPTION("top-commands.main.description", 0),
    SETUP_CMD_DESCRIPTION("top-commands.setup.description", 0),
    // generic-command-message
    NOT_AN_INTEGER("generic-command-message.argument-not-integer", 1),
    TOO_MANY_PARAMS("generic-command-message.too-many-parameters", 0),
    NOT_ENOUGH_PARAMS("generic-command-message.not-enough-parameters", 0),
    NO_PERMISSION("generic-command-message.no-permission", 1),
    PLAYER_ONLY("generic-command-message.not-a-player", 1),
    INVALID_SUB_COMMAND("generic-command-message.invalid-sub-command", 2),
    // message-colors
    COLORED_CMD_SUBCMD("message-colors.sub-command-colors", 2),
    COLORED_CMD("message-colors.base-command-colors", 1),
    COLORED_CMD_ARG("message-colors.command-argument-listing", 1),
    COLORED_HELP_MSG("message-colors.help-message-colors", 1),
    // messages
    HELP_HEADER("messages.help-message-header", 0),
    QUEUE_DEATH("messages.removed-from-queue-because-death", 0),
    NO_CLUE_COMMAND("messages.no-clue-what-command", 1),
    NOT_FULLY_ENABLED("messages.not-fully-enabled", 0),
    // messages.economy-reward
    ECO_REWARD_WIN("messages.economy-reward.win", 1),
    ECO_REWARD_KILL("messages.economy-reward.kill", 2),
    // messages.kits
    KITS_KIT_LIST("messages.kits.kit-list", 1),
    KITS_KIT_LIST_COMMA("messages.kits.kit-list-comma", 0),
    KITS_KIT_LIST_COST_ITEM("messages.kits.kit-list-item-cost", 2),
    KITS_CHOOSE_A_KIT("messages.kits.choose-a-kit", 0),
    KITS_REMOVE_A_KIT("messages.kits.remove-a-kit", 1),
    KITS_APPLIED_KIT("messages.kits.applied-kit", 1),
    KITS_APPLIED_KIT_WITH_COST("messages.kits.applied-kit-with-cost", 2),
    KITS_NOT_ENOUGH_MONEY("messages.kits.not-enough-money", 1),
    KITS_NO_PERMISSION("messages.kits.no-permission", 1),
    // messages.in-game
    GENERIC_IN_GAME("messages.in-game", 0),
    // disable report
    REPORT_DISABLED("messages.report-disabled", 0),
    // game.death
    GAME_DEATH_KILLED_BY_PLAYER_AND_VOID("game.death.killed-by-player-and-void", 2, true),
    GAME_DEATH_KILLED_BY_VOID("game.death.killed-by-void", 1, true),
    GAME_DEATH_KILLED_BY_PLAYER("game.death.killed-by-player", 2, true),
    GAME_DEATH_KILLED_BY_ENVIRONMENT("game.death.killed-by-environment", 1, true),
    GAME_DEATH_FORFEITED_WHILE_ATTACKED("game.death.forfeited-while-attacked", 2, true),
    GAME_DEATH_FORFEITED("game.death.forfeited", 2, true),
    // game.winning
    GAME_WINNING_SINGLE_WON("game.winning.single-won", 1, true),
    GAME_WINNING_MULTI_WON("game.winning.multi-won", 1, true),
    GAME_WINNING_MULTI_WON_COMMA("game.winning.multi-won-comma", 0),
    GAME_WINNING_MULTI_WON_FINAL_COMMA("game.winning.multi-won-final-comma", 0),
    GAME_WINNING_NONE_WON("game.winning.none-won", 0, true),
    // game.starting
    GAME_STARTING_GAMESTARTING("game.starting.game-starting", 1, true),
    GAME_STARTING_GAMESTARTING_COMMA("game.starting.game-starting-comma", 0),
    GAME_STARTING_GAMESTARTING_FINAL_COMMA("game.starting.game-starting-final-comma", 0),
    GAME_STARTING_TEAM_MESSAGE("game.starting.team-message", 2),
    GAME_STARTING_TEAM_COMMA("game.starting.team-message-comma", 0),
    GAME_STARTING_TEAM_FINAL_COMMA("game.starting.team-message-final-comma", 0),;
    public static final int VERSION = 4;
    public final String key;
    public final int args;
    public final boolean includePrefix;

    TransKey(final String key, final int args) {
        this(key, args, false);
    }

    TransKey(final String key, final int args, final boolean includePrefix) {
        this.key = key;
        this.args = args;
        this.includePrefix = includePrefix;
    }
}
