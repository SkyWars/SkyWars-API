/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
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

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TransKey {

    // commands.join
    CMD_JOIN_DESCRIPTION("commands.join.description", "Joins the queue for the next game"),
    CMD_JOIN_CONFIRMATION("commands.join.confirmation", "&3You have joined the queue."),
    CMD_JOIN_ALREADY_QUEUED("commands.join.already-queued", "&4You were already in the queue."),
    CMD_JOIN_IN_GAME("commands.join.in-game", "&4You can't join now, you are already in a game."),
    // commands.leave
    CMD_LEAVE_DESCRIPTION("commands.leave.description", "Leaves the queue or the game you are in"),
    CMD_LEAVE_REMOVED_FROM_QUEUE("commands.leave.removed-from-queue", "&3You are no longer in the queue."),
    CMD_LEAVE_REMOVED_FROM_GAME("commands.leave.removed-from-game", "&3You have fled the game."),
    CMD_LEAVE_NOT_IN("commands.leave.not-in-any", "&4You were not in the queue."),
    // commands.setlobby
    CMD_SETLOBBY_DESCRIPTION("commands.setlobby.description", "Sets the lobby position"),
    CMD_SETLOBBY_CONFIRMATION("commands.setlobby.confirmation", "&3The lobby is now at your current location."),
    // commands.setportal
    CMD_SETPORTAL_DESCRIPTION("commands.setportal.description", "Sets a portal at your current location"),
    CMD_SETPORTAL_CONFIRMATION("commands.setportal.confirmation", "&3You have set a portal at your current location."),
    // commands.delportal
    CMD_DELPORTAL_DESCRIPTION("commands.delportal.description", "Removes the last set portal"),
    CMD_DELPORTAL_CONFIRMATION("commands.delportal.confirmation", "&3Removed portal at %s"),
    CMD_DELPORTAL_NO_PORTAL_ERROR("commands.delportal.error-no-portals", "&4No portals are set"),
    // commands.cancel
    CMD_CANCEL_DESCRIPTION("commands.cancel.description", "Cancels a current game with the given id"),
    CMD_CANCEL_NOT_AN_INT("commands.cancel.argument-not-integer", "&c%s&4 isn't an integer!"),
    CMD_CANCEL_NO_GAMES_WITH_ID("commands.cancel.no-games-with-id", "&4There aren't any games with the id &c%s&4."),
    CMD_CANCEL_CONFIRMATION("commands.cancel.confirmation", "&3Canceling game &2%s&3."),
    // commands.status
    CMD_STATUS_DESCRIPTION("commands.status.description", "Gives status on current games"),
    CMD_STATUS_HEADER("commands.status.header", "&9 -- &2SkyWars Status&9 --"),
    CMD_STATUS_IN_QUEUE("commands.status.in-queue", "&3In Queue: &2"),
    CMD_STATUS_QUEUE_COMMA("commands.status.queue-comma", "&3,&2 "),
    CMD_STATUS_ARENA_HEADER("commands.status.arena-header", "&9 -- &2Current Arenas&9 --"),
    // commands.version
    CMD_VERSION_DESCRIPTION("commands.version.description", "Gives version"),
    CMD_VERSION_OUTPUT("commands.version.output", "&3%s version &2%s &3/ &2%s&3. Created by &2Dabo Ross"),
    // commands.lobby
    CMD_LOBBY_DESCRIPTION("commands.lobby.description", "Teleports you to the lobby"),
    CMD_LOBBY_CONFIRMATION("commands.lobby.confirmation", "&4Teleporting to the lobby."),
    CMD_LOBBY_IN_GAME("commands.lobby.in-game", "&4You can't teleport to the lobby, you are in a game."),
    // commands.cancelall
    CMD_CANCELALL_DESCRIPTION("commands.cancelall.description", "Cancels all current games."),
    CMD_CANCELALL_CANCELING("commands.cancelall.canceling-game", "&3Canceling game &2%s"),
    // commands.report
    CMD_REPORT_DESCRIPTION("commands.report.description", "Generates a report and uploads it to gist.github.com, then gives you a link."),
    CMD_REPORT_START("commands.report.start", "&3Gathering data and submitting"),
    CMD_REPORT_OUTPUT("commands.report.output", "&3Report: &2%s"),
    // setup-commands.start
    SWS_START_DESCRIPTION("setup-commands.start.description", "Start a new arena setup. If you already started setting up an arena, but didn't save it, this command will overwrite it!"),
    SWS_START_NAME_ARGUMENT("setup-commands.start.argument-name", "Arena name"),
    SWS_START_CONFIRMATION("setup-commands.start.confirmation", "&3Started setting up an arena called &2%s&3"),
    SWS_START_ONE_ARGUMENT("setup-commands.start.one-argument", "&4Please supply one and only one argument."),
    // setup-commands.setpos1
    SWS_SETPOS1_DESCRIPTION("setup-commands.setpos1.description", "Sets the first position for the arena to copy from at your current eye location."),
    SWS_SETPOS1_POS2_OTHER_WORLD("setup-commands.setpos1.pos2-in-other-world", "&4Unsetting the second position due to you being in a different world."),
    SWS_SETPOS1_CONFIRMATION("setup-commands.setpos1.confirmation", "&3Set the first position to your current eye location."),
     // setup-commands.setpos2
    SWS_SETPOS2_DESCRIPTION("setup-commands.setpos2.description", "Sets the second position for the arena to copy from at your current eye location."),
    SWS_SETPOS2_POS1_OTHER_WORLD("setup-commands.setpos2.pos1-in-other-world", "&4Unsetting the first position due to you being in a different world."),
    SWS_SETPOS2_CONFIRMATION("setup-commands.setpos2.confirmation", "&3Set the second position to your current eye location."),
    // setup-commands.addspawn
    SWS_ADDSPAWN_DESCRIPTION("setup-commands.addspawn.description", "Adds a new spawn location at your current location"),
    SWS_ADDSPAWN_CONFIRMATION("setup-commands.addspawn.confirmation", "&3Adding a new spawn location at &2%s"),
    // setup-commands.save
    SWS_SAVE_DESCRIPTION("setup-commands.save.description", "Saves the current arena setup to file."),
    SWS_SAVE_SAVING("setup-commands.save.saving", "&3Ssaving arena"),
    SWS_SAVE_SAVED("setup-commands.save.saved", "&5Arena saved"),
    // top-commands
    MAIN_CMD_DESCRIPTION("top-commands.main.description", "Main command for SkyWars"),
    SETUP_CMD_DESCRIPTION("top-commands.setup.description", "Setup command for SkyWars"),
    // generic-command-message
    TOO_MANY_PARAMS("generic-command-message.too-many-parameters", "&4Too many parameters"),
    // messages
    QUEUE_DEATH("messages.removed-from-queue-because-death", "&3You were removed from the queue because you died."),
    NO_CLUE_COMMAND("messages.no-clue-what-command", "&4SkyWars has no clue what &c%s&4 is."),
    NOT_FULLY_ENABLED("messages.not-fully-enabled", "&4Not fully enabled.");

    public final String key;
    public final String defaultValue;
}
