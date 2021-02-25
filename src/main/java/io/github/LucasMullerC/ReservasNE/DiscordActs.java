
package io.github.LucasMullerC.ReservasNE;

import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.JDA;

public class DiscordActs {
	static JDA bot;
	static TextChannel chatadmin;

	public DiscordActs(final JDA bot) {
		DiscordActs.bot = bot;
		DiscordActs.chatadmin = bot.getTextChannelById("809900341436350484");
	}

	public static String CheckDiscord(final String nome) {
		final Guild guild = DiscordActs.bot.getGuildById("735561960254341121");
		final User Builder = DiscordActs.bot.getUserByTag(nome);
		if (Builder == null) {
			return "nulo";
		}
		if (!Builder.getAsTag().equals(nome)) {
			return "nulo";
		}
		final Member member = guild.getMemberById(Builder.getId());
		if (member == null) {
			return "nulo";
		}
		return Builder.getId();
	}

	public static String getCargo(final String id) {
		final Guild guild = DiscordActs.bot.getGuildById("735561960254341121");
		final Role role = guild.getRoleById("736591730748686457");
		final Role role2 = guild.getRoleById("804321907028918293");
		final Role role3 = guild.getRoleById("804322386392252446");
		final Role role4 = guild.getRoleById("804322680055660554");
		final Member member = guild.getMemberById(id);
		if (member.getRoles().contains(role)) {
			return "t1";
		}
		if (member.getRoles().contains(role2)) {
			return "t2";
		}
		if (member.getRoles().contains(role3)) {
			return "t3";
		}
		if (member.getRoles().contains(role4)) {
			return "t4";
		}
		return "nulo";
	}

	public static void addCargo(final String UUID, final int tier, final String Discord) {
		final Guild guild = DiscordActs.bot.getGuildById("735561960254341121");
		final Member member = guild.getMemberById(Discord);
		if (tier == 2) {
			Role role = guild.getRoleById("804321907028918293");
			guild.addRoleToMember(member, role).queue();
			role = guild.getRoleById("736591730748686457");
			guild.removeRoleFromMember(member, role);
		} else if (tier == 3) {
			Role role = guild.getRoleById("804322386392252446");
			guild.addRoleToMember(member, role).queue();
			role = guild.getRoleById("804321907028918293");
			guild.removeRoleFromMember(member, role);
		} else if (tier == 4) {
			Role role = guild.getRoleById("804322680055660554");
			guild.addRoleToMember(member, role).queue();
			role = guild.getRoleById("804322386392252446");
			guild.removeRoleFromMember(member, role);
		}
	}

	public static void sendMessage(String id, String content) { // msg no privado
		User user = bot.getUserById(id);
		user.openPrivateChannel().flatMap(channel -> channel.sendMessage(content)).queue();
	}

	public static void AnalisarReserva() {
		DiscordActs.chatadmin.sendMessage(
				(CharSequence) "<@&812638293061271582> Existem areas para serem analisadas, Se estiver disponivel entre no servidor e digite **/finalizar analise**")
				.queue();
	}
}
