package test.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegexTest {
	
	private static final Logger logger = LogManager.getLogger(RegexTest.class.getName());

	public static void main(String args[]) {
		String input = "hello I'm a java dev" + "no job experience needed" + "senior software engineer"
				+ "java job available for senior software engineer";

		String fixedInput = input.replaceAll("(java|job|senior)", "<b>$1</b>");
		
		logger.debug(fixedInput);
		logger.debug("");
		
		input = "<span>Dear Team ITÂ&nbsp;</span><br><span>Sebelumnya kami ucapkan terima kasih atas supportnya selama ini</span><br><span>Â&nbsp;</span><br><span>Berikut kami informasikan permasalahan yang sedang toko kami hadapi</span><br><span>adapun permasalahan yang sedang toko kami hadapi ialah rusaknya LCD laptop toko</span><br><span>Untuk itu kami mohon bantuannya</span><br><span>Â&nbsp;</span><br><span>Demikian yang dapat kami sampaikan.</span><br><span>Â&nbsp;</span><br><span>Â&nbsp;</span><br><span>Â&nbsp;</span><br><span>Hk</span><br><span>Risna Sutrisno</span> ";
		fixedInput=input.replaceAll("<[^<>]+>",	"");
		System.out.println(input);
		System.out.println(fixedInput);
		
		input = "<span>Dear Team ITÂ&nbsp;</span><br><span>Sebelumnya kami ucapkan terima kasih atas supportnya selama ini</span><br><span>Â&nbsp;</span><br><span>Berikut kami informasikan permasalahan yang sedang toko kami hadapi</span><br><span>adapun permasalahan yang sedang toko kami hadapi ialah rusaknya LCD laptop toko</span><br><span>Untuk itu kami mohon bantuannya</span><br><span>Â&nbsp;</span><br><span>Demikian yang dapat kami sampaikan.</span><br><span>Â&nbsp;</span><br><span>Â&nbsp;</span><br><span>Â&nbsp;</span><br><span>Hk</span><br><span>Risna Sutrisno</span>";
		fixedInput = input.replaceAll("<[^<>]+>",	"");
		fixedInput = fixedInput.replaceAll("[^\\p{Print}]", "");
		fixedInput = fixedInput.replaceAll("(&nbsp;|&lt;|&gt;|&amp;|&quot;|&apos;)+", " ");
		System.out.println();
		System.out.println(input);
		System.out.println(fixedInput);
		

	}

}
