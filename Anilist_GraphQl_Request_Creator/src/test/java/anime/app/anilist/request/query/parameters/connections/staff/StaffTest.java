package anime.app.anilist.request.query.parameters.connections.staff;

import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.connections.PageInfo;
import anime.app.anilist.request.query.parameters.connections.characters.CharacterConnection;
import anime.app.anilist.request.query.parameters.connections.media.MediaArguments;
import anime.app.anilist.request.query.parameters.connections.media.MediaConnection;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateField;
import anime.app.anilist.request.query.parameters.fuzzyDate.FuzzyDateFieldParameter;
import anime.app.anilist.request.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static anime.app.anilist.request.query.parameters.connections.staff.Staff.staffTitle;
import static anime.app.anilist.request.utils.QueryTitleAndParametersMatcher.containsTitleAndAllSetElements;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class StaffTest {

	@Test
	void getStaffWithoutFieldName__ReturnCorrectString() {
		//given
		Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
				"id"
		);

		//when
		Staff actualStaff = Staff.getStaffBuilder()
				.id()
				.build();

		//then
		assertThat(actualStaff.getStaffWithoutFieldName(), allOf(
				notNullValue(),
				instanceOf(String.class),
				containsTitleAndAllSetElements(expectedStaff)
		));
	}

	@Test
	void getStaffBuilder__ReturnValidBuilder() {
		//given

		//when
		Staff.StaffBuilder builder = Staff.getStaffBuilder();

		//then
		assertThat(builder, allOf(
				notNullValue(),
				instanceOf(Staff.StaffBuilder.class)
		));
	}

	@Nested
	@DisplayName("Test Staff Builder")
	class StaffBuilderTest {
		@Test
		void staffBuilder_NoParameterSelected_ThrowException() {
			//given

			//when
			Exception thrownException = Assertions.assertThrows(
					IllegalStateException.class,
					() -> Staff.getStaffBuilder().build()
			);

			//then
			assertThat(thrownException, instanceOf(IllegalStateException.class));
			assertThat(thrownException.getMessage(), notNullValue());
		}

		@Test
		void staffBuilder_Id_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"id"
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.id()
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_Name_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"name {\nfirst\nmiddle\nlast\nfull\nnative\n}"
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.name()
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_LanguageV2_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"languageV2"
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.languageV2()
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_Image_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"image {\nlarge\nmedium\n}"
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.image()
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_DescriptionNoParameter_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"description"
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.description()
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_DescriptionWithParameter_ReturnCorrectString() {
			//given
			boolean asHtml = true;
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"description(asHtml: true)"
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.description(asHtml)
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_PrimaryOccupation_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"primaryOccupations"
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.primaryOccupations()
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_Gender_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"gender"
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.gender()
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_DateOfBirth_ReturnCorrectString() {
			//given
			FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.dateOfBirth;
			FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder()
					.allAndBuild();
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					parameter.name() + dateField.getFuzzyDateStringWithoutFieldName()
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.dateOfBirth(dateField)
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_DateOfDeath_ReturnCorrectString() {
			//given
			FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.dateOfDeath;
			FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder()
					.allAndBuild();
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					parameter.name() + dateField.getFuzzyDateStringWithoutFieldName()
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.dateOfDeath(dateField)
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_Age_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"age"
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.age()
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_YearsActive_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"yearsActive"
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.yearsActive()
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_HomeTown_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"homeTown"
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.homeTown()
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_SiteUrl_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"siteUrl"
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.siteUrl()
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_Favourites_ReturnCorrectString() {
			//given
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"favourites"
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.favourites()
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_StaffMediaWithoutArguments_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().lastPage().build();
			MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
					.pageInfo(info)
					.build();
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"staffMedia " + connection.getMediaConnectionWithoutFieldName()
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.staffMedia(connection)
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_StaffMediaWithArguments_ReturnCorrectString() {
			//given
			MediaArguments arguments = MediaArguments.getMediaArgumentsBuilder().onList().build();
			PageInfo info = PageInfo.getPageInfoBuilder().lastPage().build();
			MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
					.pageInfo(info)
					.build();
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"staffMedia" + arguments.getMediaArgumentsString() + " " + connection.getMediaConnectionWithoutFieldName()
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.staffMedia(arguments, connection)
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_CharactersWithoutArguments_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().lastPage().build();
			CharacterConnection connection = CharacterConnection.getCharacterConnectionBuilder().pageInfo(info).build();
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"characters " + connection.getCharacterConnectionWithoutFieldName()
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.characters(connection)
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_Characters_ReturnCorrectString() {
			//given
			int page = 1;
			StaffCharactersArguments arguments = StaffCharactersArguments.getStaffCharactersArgumentsBuilder()
					.page(page)
					.build();
			PageInfo info = PageInfo.getPageInfoBuilder().lastPage().build();
			CharacterConnection connection = CharacterConnection.getCharacterConnectionBuilder().pageInfo(info).build();
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"characters" + arguments.getCharacterArgumentsString() + " " + connection.getCharacterConnectionWithoutFieldName()
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.characters(arguments, connection)
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_CharacterMediaWithoutArguments_ReturnCorrectString() {
			//given
			PageInfo info = PageInfo.getPageInfoBuilder().lastPage().build();
			MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
					.pageInfo(info)
					.build();
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"characterMedia " + connection.getMediaConnectionWithoutFieldName()
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.characterMedia(connection)
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_CharacterMediaWithArguments_ReturnCorrectString() {
			//given
			int page = 1;
			StaffCharacterMediaArguments arguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
					.page(page)
					.build();
			PageInfo info = PageInfo.getPageInfoBuilder().lastPage().build();
			MediaConnection connection = MediaConnection.getMediaConnectionBuilder()
					.pageInfo(info)
					.build();
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"characterMedia" + arguments.getCharacterMediaArgumentsString() + " " + connection.getMediaConnectionWithoutFieldName()
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.characterMedia(arguments, connection)
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}

		@Test
		void staffBuilder_AllParameters_ReturnCorrectString() {
			//given
			FuzzyDateFieldParameter birthParameter = FuzzyDateFieldParameter.dateOfBirth;
			FuzzyDateFieldParameter deathParameter = FuzzyDateFieldParameter.dateOfDeath;
			FuzzyDateField birthDateField = FuzzyDateField.getFuzzyDateFieldBuilder()
					.allAndBuild();
			FuzzyDateField deathDateField = FuzzyDateField.getFuzzyDateFieldBuilder()
					.allAndBuild();
			int page = 1;
			MediaArguments mediaArguments = MediaArguments.getMediaArgumentsBuilder().onList().build();
			StaffCharacterMediaArguments staffCharacterMediaArguments = StaffCharacterMediaArguments.getStaffCharacterMediaArgumentsBuilder()
					.page(page)
					.build();
			StaffCharactersArguments staffCharactersArguments = StaffCharactersArguments.getStaffCharactersArgumentsBuilder()
					.page(page)
					.build();
			PageInfo info = PageInfo.getPageInfoBuilder().lastPage().build();
			CharacterConnection characterConnection = CharacterConnection.getCharacterConnectionBuilder().pageInfo(info).build();
			MediaConnection mediaConnection = MediaConnection.getMediaConnectionBuilder()
					.pageInfo(info)
					.build();
			Set<ParameterString> expectedStaff = TestUtils.getParameterStringSetField(
					"id",
					"name {\nfirst\nmiddle\nlast\nfull\nnative\n}",
					"languageV2",
					"image {\nlarge\nmedium\n}",
					"description",
					"primaryOccupations",
					"gender",
					birthParameter.name() + birthDateField.getFuzzyDateStringWithoutFieldName(),
					deathParameter.name() + deathDateField.getFuzzyDateStringWithoutFieldName(),
					"age",
					"yearsActive",
					"homeTown",
					"siteUrl",
					"staffMedia" + mediaArguments.getMediaArgumentsString() + " " + mediaConnection.getMediaConnectionWithoutFieldName(),
					"characters" + staffCharactersArguments.getCharacterArgumentsString() + " " + characterConnection.getCharacterConnectionWithoutFieldName(),
					"characterMedia" + staffCharacterMediaArguments.getCharacterMediaArgumentsString() + " " + mediaConnection.getMediaConnectionWithoutFieldName()
			);

			//when
			Staff actualStaff = Staff.getStaffBuilder()
					.id()
					.name()
					.languageV2()
					.image()
					.description()
					.primaryOccupations()
					.gender()
					.dateOfBirth(birthDateField)
					.dateOfDeath(deathDateField)
					.age()
					.yearsActive()
					.homeTown()
					.siteUrl()
					.staffMedia(mediaArguments, mediaConnection)
					.characters(staffCharactersArguments, characterConnection)
					.characterMedia(staffCharacterMediaArguments, mediaConnection)
					.build();

			//then
			assertThat(actualStaff.getStaffString(), allOf(
					notNullValue(),
					instanceOf(String.class),
					containsTitleAndAllSetElements(staffTitle, expectedStaff)
			));
		}
	}
}